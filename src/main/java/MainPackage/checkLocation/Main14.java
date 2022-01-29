package MainPackage.checkLocation;

import MainPackage.DTO.VehicleDto;
import MainPackage.DTO.serviceDTO;
import MainPackage.PropertiesVehicle.VehicleType;
import MainPackage.core.impl.ApplicationContext;
import MainPackage.orm.entity.Rents;
import MainPackage.orm.entity.Types;
import MainPackage.orm.service.OrdersService;
import MainPackage.orm.service.TypesService;
import MainPackage.orm.service.VehiclesService;
import MainPackage.servlets.EntityLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main14 { // comment : obj = makeProxy(implementation, obj); [ObjectFactoryImpl, 62]
    public static void main(String[] args) throws Exception {
        Map<Class<?>,Class<?>> toImpl = new HashMap<>();
        //toImpl.put(ParserVehicleInterface.class, ParserVehicleFromDB.class);
        ApplicationContext context = new ApplicationContext("MainPackage", toImpl);
        VehicleCollection vc = context.getObject(VehicleCollection.class);
        vc.display();
        //EntityLoader.typesService.save(new Types(Long.valueOf(5),"ns", 3.0));




    }
}
