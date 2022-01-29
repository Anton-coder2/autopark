package MainPackage.PropertiesVehicle.Engine;

public class ElectricalEngine extends AbstractEngine{

    Double batterySize;
    Double electricalConsumption;

    public ElectricalEngine(Double batterySize, Double electricalConsumption) {
        super("Electrical",  1.0);
        this.batterySize = batterySize;
        this.electricalConsumption = electricalConsumption;
    }

    public double getBatterySize() {
        return batterySize;
    }

    public void setBatterySize(double batterySize) {
        this.batterySize = batterySize;
    }

    public double getElectricalConsumption() {
        return electricalConsumption;
    }

    public void setElectricalConsumption(double electricalConsumption) {
        this.electricalConsumption = electricalConsumption;
    }


    @Override
    public double getTaxPerMonth() {
        return taxKoef;
    }

    @Override
    public double getFuelPer100km() {
        return 0;
    }

    @Override
    public double getMaxKilometrs() {
        return  batterySize / electricalConsumption;
    }

    @Override
    public String toString(){
        return "(Electrical Engine " + batterySize +"," + electricalConsumption + ")";
    }
}
