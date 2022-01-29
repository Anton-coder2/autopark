package MainPackage.servlets;

import MainPackage.DTO.VehicleDto;
import MainPackage.DTO.serviceDTO;
import MainPackage.PropertiesVehicle.Rent;
import MainPackage.PropertiesVehicle.Vehicle;
import MainPackage.PropertiesVehicle.VehicleType;
import MainPackage.checkLocation.VehicleCollection;
import MainPackage.checkLocation.Workroom;
import MainPackage.core.Context;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.core.impl.ApplicationContext;
import MainPackage.orm.EntityManager;
import MainPackage.orm.entity.*;
import MainPackage.orm.impl.EntityManagerImpl;
import MainPackage.orm.service.*;
import MainPackage.threads.Collect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityLoader {

    static Map<Class<?>,Class<?>> toImpl = new HashMap<>();
    static ApplicationContext context = new ApplicationContext("MainPackage",toImpl);
    public static TypesService typesService;

    static {
        try {
            typesService = context.getObject(TypesService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<VehicleType> getTypes() throws Exception {
        return getCollection().getTypes();
    }
    public static Collect getCollect() throws Exception {
        return context.getObject(Collect.class);
    }
    public static List<VehicleDto> getVehicles() throws Exception {
        return context.getObject(serviceDTO.class).getVehicles();
    }
    public static List<Orders> getOrders() throws Exception {
        return getCollection().getOrders();
    }
    public static List<Rent> getRents() throws Exception {
        return getCollection().getRents();
    }
    public static VehicleCollection getCollection() throws Exception {
        return context.getObject(VehicleCollection.class);
    }
    public static Workroom getWorkRoom() throws Exception {
        return context.getObject(Workroom.class);
    }
    public static void setTypes(VehicleType type) throws Exception {
        getCollection().getTypes().add(type);
    }

}
