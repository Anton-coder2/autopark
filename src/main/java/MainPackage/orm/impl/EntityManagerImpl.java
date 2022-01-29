package MainPackage.orm.impl;

import MainPackage.core.Context;
import MainPackage.core.annotations.Autowired;
import MainPackage.orm.ConnectionFactory;
import MainPackage.orm.EntityManager;
import MainPackage.orm.service.PostgreDataBaseService;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {
    @Autowired
    private ConnectionFactory connection;
    @Autowired
    private PostgreDataBaseService dataBaseService;
    @Autowired
    private Context context;

    public EntityManagerImpl() {}

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        Optional<T> res = Optional.empty();
        try {
            res = dataBaseService.get(id, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    @Override
    public Long save(Object object) throws Exception {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        List<T> res = new ArrayList<>();
        try {
            res = dataBaseService.getAll(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void delete(int id, Class<?> clazz) {
        dataBaseService.delete(Long.valueOf(id),clazz);
    }
}
