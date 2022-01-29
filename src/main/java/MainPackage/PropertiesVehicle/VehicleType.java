package MainPackage.PropertiesVehicle;

import lombok.ToString;

@ToString
public class VehicleType {

    private int typeId;
    private String typeName;
    private double taxCoefficient;

    public VehicleType(String typeName, double taxCoefficient) {
        this.typeName = typeName;
        this.taxCoefficient = taxCoefficient;
    }
    public VehicleType(int typeId,String typeName, double taxCoefficient) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.taxCoefficient = taxCoefficient;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    public String display(){
        return "typeName = " + typeName + "\n" +"taxCoefficient = " + taxCoefficient;
    }

    public String getString(){ return typeId +  "," + typeName + "," + taxCoefficient;}
}
