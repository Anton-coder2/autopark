package MainPackage.checkLocation;

import MainPackage.PropertiesVehicle.*;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.orm.entity.Orders;
import MainPackage.orm.entity.Rents;
import MainPackage.orm.entity.Types;
import MainPackage.orm.entity.Vehicles;
import MainPackage.orm.service.TypesService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleCollection {

    private List<VehicleType> vehicleTypes = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Rent> rents = new ArrayList<>();
    private List<Orders> orders = new ArrayList<>();

    @Autowired
    private TypesService typesService;

    @Autowired
    private ParserVehicleInterface parser;

    @Autowired
    private ParserVehicleFromFile parserVehicleFromFile;

    public VehicleCollection() {

    }

    public List<VehicleType> getTypes() {
        return vehicleTypes;
    }

    public void setTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public ParserVehicleInterface getParser() {
        return parser;
    }

    public void setParser(ParserVehicleInterface parser) {
        this.parser = parser;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @InitMethod
    public void init() throws Exception {

       // parserVehicleFromFile.loadVehicleTypeList();

        vehicleTypes = parser.loadTypes();
        vehicles = parser.loadVehicles();
        rents = parser.loadRents();
        orders = parser.loadOrders();

        // Заполнение выручки за прокат
        vehicles.stream().forEach(y -> y.setProfit(rents.stream().filter(x -> x.getId() ==y.getId()).collect(Collectors.summingDouble(x -> x.getCost()))));

        // Заполнение финального дохода
        vehicles.stream().forEach(y -> y.setIncome((y.getProfit())-y.getCalcTaxPerMonth()));

    }
    public void display(){
        for(Vehicle i: vehicles){
            System.out.println(i.toString());
        }

    }
}

