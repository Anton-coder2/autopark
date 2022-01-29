package MainPackage.checkLocation;

import MainPackage.PropertiesVehicle.NotVehicleException;
import MainPackage.PropertiesVehicle.Rent;
import MainPackage.PropertiesVehicle.Vehicle;
import MainPackage.PropertiesVehicle.VehicleType;
import MainPackage.orm.entity.Orders;
import MainPackage.orm.entity.Rents;
import MainPackage.orm.entity.Types;

import java.util.List;

public interface ParserVehicleInterface {
    List<Vehicle> loadVehicles() throws NotVehicleException;
    List<Rent> loadRents();
    List<VehicleType> loadTypes();
    List<Orders> loadOrders();
}
