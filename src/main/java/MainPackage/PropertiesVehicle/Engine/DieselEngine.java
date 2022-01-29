package MainPackage.PropertiesVehicle.Engine;

public class DieselEngine extends CombustionEngine{
    public DieselEngine( Double engineCapacity, Double fuelTankCapacity, Double fuelConsumptionPer100) {
        super("Diesel", 1.2, engineCapacity, fuelTankCapacity, fuelConsumptionPer100);
    }
    @Override
    public String toString(){
        return "(DieselEngine " + engineCapacity +"," + fuelTankCapacity + "," + fuelConsumptionPer100 + ")";
    }


}
