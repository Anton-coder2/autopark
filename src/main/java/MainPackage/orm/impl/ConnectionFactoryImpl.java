package MainPackage.orm.impl;

import MainPackage.core.annotations.InitMethod;
import MainPackage.core.annotations.Property;
import MainPackage.orm.ConnectionFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;
    @Property("username")
    private String username;
    @Property("password")
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {}

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "postgres");
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
