package MainPackage.orm.service;

import MainPackage.core.Context;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.orm.ConnectionFactory;
import MainPackage.orm.annotations.Column;
import MainPackage.orm.annotations.ID;
import MainPackage.orm.annotations.Table;
import MainPackage.orm.enums.SqlFieldType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PostgreDataBaseService {
    @Autowired
    private ConnectionFactory connectionFactory;
    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    @Autowired
    private Context context;

    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN = "SELECT EXISTS (\n" +
            "SELECT FROM information_schema.sequences \n" +
            "WHERE  sequence_schema = 'public' \n" +
            "AND    sequence_name   = '%s' \n" +
            ");";
    private static final String CREATE_ID_SEQ_PATTERN = "CREATE SEQUENCE %S INCREMENT 1 START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN = "SELECT EXISTS (\n" +
            "SELECT FROM information_schema.tables \n" +
            "WHERE  table_schema = 'public' \n" +
            "AND    table_name   = '%s' \n" +
            ");";
    private static final String CREATE_TABLE_SQL_PATTERN = "CREATE TABLE %s (\n " +
            "%s integer PRIMARY KEY DEFAULT nextval('%s'), " +
            "%S\n);";
    private static final String INSERT_SQL_PATTERN = "INSERT INTO %s(%s) \n" +
            "VALUES (%s) \n " +
            "RETURNING %s ;";
    private static final String DELETE_SQL_PATTERN = "DELETE FROM %s WHERE id = %s;";
    private Map<String, String> insertByClassPattern;
    private Map<String, String> deleteByClassPattern;

    public PostgreDataBaseService() {}

    @SneakyThrows
    @InitMethod
    public void init() throws Exception {
        classToSql = new HashMap<>();
        insertPatternByClass = new HashMap<>();
        insertByClassPattern = new HashMap<>();
        deleteByClassPattern = new HashMap<>();

        Arrays.stream(SqlFieldType.values()).forEach(x -> classToSql.put(x.getType(),x.getSqlType()));
        Arrays.stream(SqlFieldType.values()).forEach(x -> insertPatternByClass.put(x.getType(),x.getInsertPattern()));

        boolean exists = existsSequence();
        if(!exists) {
            createSequence();
        }
        var tableEntities = context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);
        for(var entity : tableEntities) {
            try {
                checkEntity(entity);
            } catch (Exception e) {
                throw new Exception("Entity " + entity.getName() + " is incorrect");
            }
        }
        for(var entity : tableEntities) {
            exists = existsTable(entity);
            if(!exists) {
                createTable(entity);
            }
        }

        tableEntities.stream().forEach(x -> {
            try {
                insertByClassPattern.put(x.getSimpleName(),createSqlRow(x));
                deleteByClassPattern.put(x.getSimpleName(),createDeleteSqlRow(x));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private boolean existsSequence() throws Exception {
        boolean exists = false;
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            String sql = String.format(CHECK_SEQ_SQL_PATTERN, SEQ_NAME);
            resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                exists = resultSet.getBoolean("exists");
            }
            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            throw new Exception("SQLException in PostgreDataBaseService.existsSequence()");
        }
        return exists;
    }

    private void createSequence() throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);
            statement.execute(sql);
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            throw new Exception("SQLException in PostgreDataBaseService.createSequence()");
        }
    }

    private void checkEntity(Class<?> entity) throws Exception {
        Field[] fields = entity.getDeclaredFields();
        int idCount = 0;
        Set<String> columnName = new HashSet<>();
        for(Field field : fields) {
            if(field.isAnnotationPresent(ID.class) && field.getType().equals(Long.class)) {
                idCount++;
            } else if(field.isAnnotationPresent(Column.class)) {
                if(!columnName.contains(field.getName()) &&
                        !field.getType().isPrimitive()) {
                    columnName.add(field.getName());
                } else {
                    throw new Exception("field " + field.getName() + " name or type is incorrect");
                }
            } else {
                throw new Exception("field " + field.getName() + " don't have an annotation `ID` or `Column`");
            }
        }
        if(idCount != 1) {
            throw new Exception(entity.getName() + " have 0 or more than one annotation `ID`");
        }
    }

    private boolean existsTable(Class<?> entity) throws Exception {
        boolean exists = false;
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            String sql = String.format(CHECK_TABLE_SQL_PATTERN, entity.getSimpleName().toLowerCase());
            resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                exists = resultSet.getBoolean("exists");
            }
            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            throw new Exception("SQLException in PostgreDataBaseService.existsTable()");
        }
        return exists;
    }

    private void createTable(Class<?> entity) throws Exception {
        String tableName = entity.getSimpleName();
        String idField = Arrays.stream(entity.getDeclaredFields()).filter(x -> x.isAnnotationPresent(ID.class)).findFirst().get().getName();
        Field[] fields = entity.getDeclaredFields();
        StringBuilder fieldsRow = new StringBuilder();

        for(Field field : fields) {
            if(field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                fieldsRow.append(field.getName()).append(" ");
                fieldsRow.append(classToSql.get(field.getType().getSimpleName()+".class")).append(" ");
                if(column.nullable()) {
                    fieldsRow.append("NOT NULL").append(" ");
                }
                if(column.unique()) {
                    fieldsRow.append("UNIQUE").append(" ");
                }
                fieldsRow.append(",");
            }
        }
        if (fieldsRow.length() > 0) {
            fieldsRow.setLength(fieldsRow.length() - 1);
        }
       // fieldsRow.deleteCharAt(fieldsRow.length()-1);
        Connection connection;
        Statement statement;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            //System.out.println(CREATE_TABLE_SQL_PATTERN);
            System.out.println(fieldsRow);
            String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fieldsRow);
            //System.out.println(sql);
            statement.execute(sql);
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            throw new Exception("SQLException in PostgreDataBaseService.createTable()" + e.getCause() + e.getMessage());
        }
    }

    private String createSqlRow(Class<?> entity) throws ClassNotFoundException {
        String tableName = entity.getSimpleName();
        StringBuilder insertFields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        /*Arrays.stream(entity.getDeclaredFields()).
                filter(x ->x.isAnnotationPresent(Column.class)).
                forEach(x->insertFields.append(x.getName()).append(","));*/

        Arrays.stream(entity.getDeclaredFields()).
                forEach(x->insertFields.append(x.getName()).append(","));

        if (insertFields.length() > 0) {
            insertFields.setLength(insertFields.length() - 1);
        }
        /*for(Field field : entity.getDeclaredFields()) {
            if(field.isAnnotationPresent(Column.class)) {
               // System.out.println(Class.forName(field.getType().getSimpleName() +".class"));
               //   System.out.println(insertPatternByClass.get(field.getType().getSimpleName()+".class"));
                values.append(insertPatternByClass.get(field.getType().getSimpleName()+".class")).append(",");
            }
        }*/
        Arrays.stream(entity.getDeclaredFields()).
                forEach(x->values.append(insertPatternByClass.get(x.getType().getSimpleName()+".class")).append(","));

        if (values.length() > 0) {
            values.setLength(values.length() - 1);
        }
        String idFieldName = Arrays.stream(entity.getDeclaredFields()).filter(x -> x.isAnnotationPresent(ID.class)).findFirst().get().getName();
        //String s = String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idFieldName);
        //System.out.println(values);
       // System.out.println(insertFields);
        return String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idFieldName);
    }

    private String createDeleteSqlRow(Class<?> entity){
         String tableName = entity.getSimpleName();
         //System.out.println(DELETE_SQL_PATTERN);
         String s =String.format(DELETE_SQL_PATTERN, tableName, "%s");
         //System.out.println(s);
         return s;
    }

    public Long save(Object obj) throws Exception {
        Object[] values = getValues(obj);
        //System.out.println(insertByClassPattern.get(obj.getClass().getSimpleName()));
        //System.out.println(insertByClassPattern.get(obj.getClass().getSimpleName()));

        String sql = String.format(insertByClassPattern.get(obj.getClass().getSimpleName()), values);

        System.out.println(sql);
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        Long id;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
           /* Long id = resultSet.getLong("id");
            for (Field field : obj.getClass().getDeclaredFields()){
                if(field.isAnnotationPresent(ID.class)){
                    field.setAccessible(true);
                    field.set(obj,id);
                }
             }*/
            id = Long.valueOf(1);
          // resultSet.close();
           // statement.close();
         // connection.close();
        } catch (SQLException e) {
            throw new Exception("SQLException in PostgreDataBaseService.save()" + e.getMessage() + e.getCause() );
        }
        return id;
    }

    public void delete(Long id, Class<?> clazz){
        //System.out.println(deleteByClassPattern.get(clazz.getSimpleName()));
        String sql = String.format(deleteByClassPattern.get(clazz.getSimpleName()),id);
        Connection connection;
        Statement statement;

        connection = connectionFactory.getConnection();

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public <T> Optional<T> get(Long id, Class<T> clazz) throws Exception {
        if(!clazz.isAnnotationPresent(Table.class)) {
            throw new Exception("class without annotation `Table`");
        }
        String idFieldName = Arrays.stream(clazz.getDeclaredFields()).filter(x -> x.isAnnotationPresent(ID.class)).findFirst().get().getName();
        String sql = "SELECT * FROM " + clazz.getName() + " WHERE " + idFieldName + " = " + id;
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        T object = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            object = makeObject(resultSet, clazz);
            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            throw new Exception("SQLException in PostgreDataBaseService.get()" );
        }
        return Optional.of(object);
    }

    public <T> List<T> getAll(Class<T> clazz) throws Exception {
        if(!clazz.isAnnotationPresent(Table.class)) {
            throw new Exception("class without annotation `Table`");
        }
        String sql = "SELECT * FROM " + clazz.getSimpleName();
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        List<T> list = new ArrayList<>();
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(makeObject(resultSet, clazz));
            }
            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            throw new Exception("SQLException in PostgreDataBaseService.getAll()");
        }
        return list;
    }

    private Object[] getValues(Object obj) {
        List<Object> objects = new ArrayList<>();
        for(Field field : obj.getClass().getDeclaredFields()) {
            //if(field.isAnnotationPresent(Column.class)) {
                try {
                    objects.add(field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
           // }
        }
        return objects.toArray();
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        T t = clazz.getDeclaredConstructor().newInstance();
        for (var field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            if (field.isAnnotationPresent(ID.class))
                field.set(t, resultSet.getLong(field.getName()));
            if (field.isAnnotationPresent(Column.class)){
                //Column column = field.getDeclaredAnnotation(Column.class);
                String fieldName = field.getName();
                Object res = resultSet.getObject(fieldName);

                if(res.getClass().equals(BigDecimal.class) && field.getType().equals(Double.class)) {
                    res = ((BigDecimal)res).doubleValue();
                }
                if(res.getClass().equals(Integer.class) && field.getType().equals(Long.class)) {
                    res = ((Integer)res).longValue();
                }
                field.set(t, res);
            }
        }
        return t;
    }
}
