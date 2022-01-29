package MainPackage.orm.service;

import MainPackage.PropertiesVehicle.Rent;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.orm.EntityManager;
import MainPackage.orm.entity.Rents;

import java.util.ArrayList;
import java.util.List;

public class RentsService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {}

    public Rents get(Long id) {
        return entityManager.get(id, Rents.class).get();
    }

    public List<Rent> getAll() {

        List<Rents> rentsList = entityManager.getAll(Rents.class);
        List<Rent> rentList = new ArrayList<>();
        for(Rents rents : rentsList){
            rentList.add(new Rent((int)rents.getVehicleId().longValue(),rents.getRentDate().toString(),rents.getCost()));
        }


        return rentList ;
    }
    public Long save(Rents type) throws Exception {
        return entityManager.save(type);
    }
}
