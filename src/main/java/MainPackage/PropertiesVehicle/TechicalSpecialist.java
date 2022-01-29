package MainPackage.PropertiesVehicle;

import MainPackage.PropertiesVehicle.Engine.CombustionEngine;
import MainPackage.PropertiesVehicle.Engine.ElectricalEngine;
import MainPackage.PropertiesVehicle.Engine.GasolineEngine;
import MainPackage.PropertiesVehicle.Engine.Startable;

//import static WholePackage.PackageToScan.Entity.VehicleCollection.createType;

public class TechicalSpecialist {

    public static final int LowerLimitManufactureYear = 1886;

    public TechicalSpecialist(){

    }

    public int validateManufactureYear(int year) throws NotVehicleException {
        if(year>= LowerLimitManufactureYear){
            return year;
        }
        else{
            throw new NotVehicleException("ошибка год");
        }
    }

    public int validateMileage(int mileage) throws NotVehicleException {
        if(mileage>= 0){
            return mileage;
        }
        else{
            throw new NotVehicleException("ошибка пробег");
        }
    }

    public int validateWeight(int weight) throws NotVehicleException {
        if(weight>= 0){
            return weight;
        }
        else{
            throw new NotVehicleException("ошибка вес");}
    }

    public Color validateColorString(String color) throws NotVehicleException {

        for(Color color1 : Color.values()){
            if(color1.toString().equals(color)){
                return color1;
            }
        }
            throw new NotVehicleException("ошибка цвет");
    }

   /* public VehicleType validateVehicleType(String type) throws NotVehicleException {
      String [] s = type.split(",");
      if(s[0].isEmpty() == false && Double.parseDouble(s[1])>0.0)
          //return createType(type);

      else
        throw new NotVehicleException("Type error");
    }*/

    public String validateRegistrationNumber(String number) throws NotVehicleException {
        if(number.matches( "\\d{4}\\s+[A-Z]{2}+[-]+\\d")){
            return number;
        }
        else
            throw new NotVehicleException("Type error");

    }

    public String validateModuleName(String number) throws NotVehicleException {
        if(number != null){
            return number;
        }
        else
            throw new NotVehicleException("Number error");
    }

    public Startable validateElectricalEngine(ElectricalEngine name) throws NotVehicleException {
        if(name.getBatterySize()>0 && name.getElectricalConsumption()>0 && name.getTaxPerMonth()>0){
            return name;
        }
        else{
            throw new NotVehicleException("Engine error");
        }
    }

    public Startable validateGasolineEngine(GasolineEngine name) throws NotVehicleException {
        if(name.getEngineCapacity()>0 && name.getFuelTankCapacity()>0 && name.getFuelPer100km()>0 && name.getTaxPerMonth()>0){
            return name;
        }
        else{
            throw new NotVehicleException("Engine error");
        }
    }

    public Startable validateDieselEngine(CombustionEngine name) throws NotVehicleException {
        if(name.getEngineCapacity()>0 && name.getFuelTankCapacity()>0 && name.getFuelPer100km()>0 && name.getTaxPerMonth()>0){
            return name;}
        else{
            throw new NotVehicleException("Engine error");
        }
    }



}
