package MainPackage.checkLocation;

import MainPackage.PropertiesVehicle.Vehicle;
import MainPackage.core.Context;
import MainPackage.core.annotations.Autowired;
import MainPackage.orm.entity.Orders;
import MainPackage.orm.entity.Vehicles;
import MainPackage.orm.service.OrdersService;
import MainPackage.orm.service.TypesService;
import MainPackage.orm.service.VehiclesService;
import MainPackage.threads.anntotations.Schedule;

import java.util.*;

public class Workroom {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private VehiclesService vehiclesService;

    private List<Long> listVehicleId = new ArrayList<>();
    @Autowired
    private Context context;

    Set<Long> badVehiclesId;
    public Workroom(){

    }

    public void newDetectBreaking() throws Exception {
        for(int i = 1; i<7; i++){
            ordersService.delete(i);
        }
        listVehicleId.clear();
        String[] details = {"Фильтр","Втулка","Вал","Ось", "Свеча","Масло", "ГРМ","ШРУС"};

        List<Vehicle> list = vehiclesService.getAll();
        Random random = new Random();
        int randomNumberVehicles = random.nextInt(list.size()+1);

        for(int i =1;i<randomNumberVehicles+1; i++) {

            Long randomVehicleId;
            while (true)
            {
                randomVehicleId= Long.valueOf(list.get(random.nextInt(list.size())).getId());
                if(!listVehicleId.contains(randomVehicleId)){
                    listVehicleId.add(randomVehicleId);
                    break;
                }
            }

            int randomCountsOfDetail = random.nextInt(6)+1;
            String randomDetail = details[random.nextInt(8)];

            Orders myOrder = new Orders(Long.valueOf(i),randomVehicleId,randomDetail,randomCountsOfDetail);

            ordersService.save(myOrder);
        }

    }
    public Set checkAllVehicle(VehicleCollection collection) throws Exception {
        List<Orders> orders = context.getObject(OrdersService.class).getAll();
        List<Vehicle> vehicles = collection.getVehicles();
        Set<Integer> badVehiclesId = new HashSet<>();
        for (Orders order : orders) {
            Long vehicleID = order.getVehicleID();
            if(!badVehiclesId.contains(vehicleID)) {
                badVehiclesId.add(Math.toIntExact(vehicleID));
                Vehicle v = vehicles.stream().filter(x -> x.getId() == (int)vehicleID.longValue()).findFirst().get();
                System.out.println(v + " is broken");
            }
        }

        for (Vehicle vehicle : vehicles) {
            if(!badVehiclesId.contains(vehicle.getId())) {
                System.out.println(vehicle + " is not broken");
            }
        }
        return badVehiclesId;
    }
    /*public Set checkAllVehicle(VehicleCollection collection) throws Exception {
        List<Orders> orders = context.getObject(OrdersService.class).getAll();
        List<Vehicle> vehicles = collection.getVehicles();
        Set<Integer> badVehiclesId = new HashSet<>();
        for (Orders order : orders) {
            Long vehicleID = order.getVehicleID();
            if(!badVehiclesId.contains(vehicleID)) {
                badVehiclesId.add(Math.toIntExact(vehicleID));
                Vehicle v = vehicles.stream().filter(x -> x.getId() == (int)vehicleID.longValue()).findFirst().get();
                System.out.println(v + " is broken");
            }
        }

        for (Vehicle vehicle : vehicles) {
            if(!badVehiclesId.contains(vehicle.getId())) {
                System.out.println(vehicle + " is not broken");
            }
        }
        return badVehiclesId;
    }*/
}
