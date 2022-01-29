package MainPackage.orm.service;

import MainPackage.PropertiesVehicle.Color;
import MainPackage.PropertiesVehicle.Engine.CombustionEngine;
import MainPackage.PropertiesVehicle.Engine.DieselEngine;
import MainPackage.PropertiesVehicle.Engine.ElectricalEngine;
import MainPackage.PropertiesVehicle.Engine.GasolineEngine;
import MainPackage.PropertiesVehicle.NotVehicleException;
import MainPackage.PropertiesVehicle.Vehicle;
import MainPackage.PropertiesVehicle.VehicleType;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.orm.EntityManager;
import MainPackage.orm.entity.Types;
import MainPackage.orm.entity.Vehicles;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class VehiclesService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init() {}

    public Vehicles get(Long id) {
        return entityManager.get(id, Vehicles.class).get();
    }

    public List<Vehicle> getAll() throws NotVehicleException {

        List<Vehicles> vehiclesList = entityManager.getAll(Vehicles.class);
        List<Vehicle> vehicleList = new ArrayList<>();
        List<Types> typesList = entityManager.getAll(Types.class);

        for(Vehicles vehicles : vehiclesList){
            Vehicle vehicle = null;
            if (vehicles.getEngineType().equals("gasoline")){
                vehicle = new Vehicle((int) vehicles.getId().longValue(),
                        new VehicleType(
                                Math.toIntExact(vehicles.getTypeId()),
                                typesList.get(((int) (vehicles.getTypeId().longValue() - 1))).getName(),
                                (Double) typesList.get((int) (vehicles.getTypeId().longValue() - 1)).getCoefTaxes().doubleValue()),




                        vehicles.getModelName(),
                        vehicles.getRegistrationNumber(), vehicles.getWeight(), vehicles.getManufactureYear(),
                        vehicles.getMileage(), Color.valueOf(vehicles.getColor()), new GasolineEngine(vehicles.getEngineCapacity(), vehicles.getFuelTankCapacity(), vehicles.getFuelConsumptionPer100()));
            }
            if(vehicles.getEngineType().equals("diesel"))
                    vehicle = new Vehicle((int)vehicles.getId().longValue(),
                            new VehicleType(
                                    Math.toIntExact(vehicles.getTypeId()),
                                    typesList.get(((int) (vehicles.getTypeId().longValue() - 1))).getName(),
                                    (Double) typesList.get((int) (vehicles.getTypeId().longValue() - 1)).getCoefTaxes().doubleValue()),
                            vehicles.getModelName(),
                            vehicles.getRegistrationNumber(),vehicles.getWeight(),vehicles.getManufactureYear(),
                            vehicles.getMileage(),Color.valueOf(vehicles.getColor()), new DieselEngine(vehicles.getEngineCapacity(),vehicles.getFuelTankCapacity(),vehicles.getFuelConsumptionPer100()));
            if(vehicles.getEngineType().equals( "electrical")) {
                    vehicle = new Vehicle((int) vehicles.getId().longValue(),
                            new VehicleType(
                                    Math.toIntExact(vehicles.getTypeId()),
                                    typesList.get(((int) (vehicles.getTypeId().longValue() - 1))).getName(),
                                    (Double) typesList.get((int) (vehicles.getTypeId().longValue() - 1)).getCoefTaxes().doubleValue())
                        ,
                        vehicles.getModelName(),
                        vehicles.getRegistrationNumber(), vehicles.getWeight(), vehicles.getManufactureYear(),
                        vehicles.getMileage(), Color.valueOf(vehicles.getColor()), new ElectricalEngine(vehicles.getEngineCapacity(), vehicles.getFuelConsumptionPer100()));
            }

            vehicleList.add(vehicle);
        }

        return vehicleList;
    }
    public Long save(Vehicles type) throws Exception {
        return entityManager.save(type);
    }
}
