package MainPackage.threads;

import MainPackage.checkLocation.VehicleCollection;
import MainPackage.checkLocation.Workroom;
import MainPackage.core.impl.ApplicationContext;
import MainPackage.orm.ConnectionFactory;
import MainPackage.orm.impl.ConnectionFactoryImpl;


import java.util.HashMap;
import java.util.Map;

public class Main15 {
    public static void main(String[] args) throws Exception {
        Map<Class<?>,Class<?>> toImpl = new HashMap<>();
        ApplicationContext context = new ApplicationContext("MainPackage", toImpl);
        /*ConnectionFactory connectionFactory = context.getObject(ConnectionFactory.class);*/
        VehicleCollection collection = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);
        Collect c = context.getObject(Collect.class);
        //c.collect();

       try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
