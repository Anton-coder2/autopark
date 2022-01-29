package MainPackage.PropertiesVehicle.Engine;

public abstract class CombustionEngine extends AbstractEngine {

    Double engineCapacity;
    Double fuelTankCapacity;
    Double fuelConsumptionPer100;

    public CombustionEngine(String engineName, double taxKoef, double engineCapacity, double fuelTankCapacity, double fuelConsumptionPer100) {
        super(engineName, taxKoef);
        this.engineCapacity = engineCapacity;
        this.fuelTankCapacity = fuelTankCapacity;
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
    }

    @Override
    public double getTaxPerMonth() {
        return taxKoef;
    }

    @Override
    public double getMaxKilometrs() {
        return  fuelTankCapacity / fuelConsumptionPer100;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    @Override
    public double getFuelPer100km() {
        return fuelConsumptionPer100;
    }

}
