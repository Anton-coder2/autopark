package MainPackage.checkLocation;

import MainPackage.core.impl.ApplicationContext;
import MainPackage.orm.entity.Rents;
import MainPackage.servlets.EntityLoader;

import java.util.HashMap;
import java.util.Map;

public class Main16 {
    public static void main(String[] args) throws Exception {
        //System.out.println(EntityLoader.getTypes().size());
        Map<Class<?>,Class<?>> toImpl = new HashMap<>();
        //toImpl.put(ParserVehicleInterface.class, ParserVehicleFromDB.class);
        ApplicationContext context = new ApplicationContext("MainPackage", toImpl);
        VehicleCollection collection = context.getObject(VehicleCollection.class);

        Workroom workroom = context.getObject(Workroom.class);

        workroom.newDetectBreaking();
        //workroom.checkAllVehicle(collection);
    }
}
