package MainPackage.checkLocation;

import MainPackage.PropertiesVehicle.NotVehicleException;
import MainPackage.PropertiesVehicle.Rent;
import MainPackage.PropertiesVehicle.Vehicle;
import MainPackage.PropertiesVehicle.VehicleType;
import MainPackage.core.annotations.Autowired;
import MainPackage.orm.entity.Orders;
import MainPackage.orm.entity.Rents;
import MainPackage.orm.service.OrdersService;
import MainPackage.orm.service.RentsService;
import MainPackage.orm.service.TypesService;
import MainPackage.orm.service.VehiclesService;

import java.util.List;

public class ParserVehicleFromDB implements ParserVehicleInterface {

    @Autowired
    private TypesService typesService;
    @Autowired
    private VehiclesService vehiclesService;
    @Autowired
    private RentsService rentsService;
    @Autowired
    private OrdersService ordersService;

    @Override
    public List<Vehicle> loadVehicles() throws NotVehicleException {
        return vehiclesService.getAll();
    }

    @Override
    public List<Rent> loadRents() {
        return rentsService.getAll();
    }

    @Override
    public List<VehicleType> loadTypes() {
        return typesService.getAll();
    }

    @Override
    public List<Orders> loadOrders() {
        return ordersService.getAll();
    }
}
