package MainPackage.orm.service;

import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.orm.EntityManager;
import MainPackage.orm.entity.Orders;

import java.util.List;

public class OrdersService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init() {}
    public Orders get(Long id) {
        return entityManager.get(id, Orders.class).get();
    }
    public List<Orders> getAll() {
        return entityManager.getAll(Orders.class);
    }
    public Long save(Orders type) throws Exception {
        return entityManager.save(type);
    }
    public void delete(int id) {
        entityManager.delete(id,Orders.class);
    }
}
