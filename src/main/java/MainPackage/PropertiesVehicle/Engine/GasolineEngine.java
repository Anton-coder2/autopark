package MainPackage.PropertiesVehicle.Engine;

public class GasolineEngine extends CombustionEngine{

    public GasolineEngine( Double engineCapacity, Double fuelTankCapacity, Double fuelConsumptionPer100) {
        super("Gasoline", 1.2, engineCapacity, fuelTankCapacity, fuelConsumptionPer100);
    }

    @Override
    public String toString(){
        return "(GasolineEngine " + engineCapacity +"," + fuelTankCapacity + "," + fuelConsumptionPer100 + ")";
    }

}
