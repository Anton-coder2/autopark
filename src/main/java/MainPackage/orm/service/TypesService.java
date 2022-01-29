package MainPackage.orm.service;

import MainPackage.PropertiesVehicle.VehicleType;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.orm.EntityManager;
import MainPackage.orm.entity.Types;

import java.util.ArrayList;
import java.util.List;

public class TypesService {
    @Autowired
    EntityManager entityManager;

    public TypesService() {}

    @InitMethod
    public void init() {}
    public Types get(Long id) {
        return entityManager.get(id, Types.class).get();
    }
    public List<VehicleType> getAll() {
        List<VehicleType> vehicleTypeList = new ArrayList<>();
        for(Types type : entityManager.getAll(Types.class)){
            vehicleTypeList.add(new VehicleType(Math.toIntExact(type.getId()),type.getName(), type.getCoefTaxes()));
        };
        return vehicleTypeList;
    }
    public Long save(Types type) throws Exception {
        return entityManager.save(type);
    }
}
