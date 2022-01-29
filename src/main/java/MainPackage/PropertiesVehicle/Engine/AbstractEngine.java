package MainPackage.PropertiesVehicle.Engine;

public abstract class AbstractEngine implements Startable{

    String engineName;
    double taxKoef;

    public AbstractEngine(String engineName, double taxKoef) {
        this.engineName = engineName;
        this.taxKoef = taxKoef;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public double getTaxKoef() {
        return taxKoef;
    }

    public void setTaxKoef(int taxKoef) {
        this.taxKoef = taxKoef;
    }

}
