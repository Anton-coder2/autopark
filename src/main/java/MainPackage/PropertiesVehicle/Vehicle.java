package MainPackage.PropertiesVehicle;

import MainPackage.PropertiesVehicle.Engine.Startable;
import MainPackage.checkLocation.VehicleCollection;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.annotations.InitMethod;
import MainPackage.orm.service.RentsService;
import lombok.*;


import java.util.*;
import java.util.stream.Collectors;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    private int id;
    private VehicleType type;
    private String model;
    private int age;
    private String number;
    private int mass;
    private int run;
    private Color color;
    private int tankVolume;
    private int VolumeK;
    private Startable engine;
    private double profit;
    private double income;

    public Vehicle(int id, VehicleType type, String model, String number,int mass, int age,int run,  Color color, Startable engine) throws NotVehicleException {

        this.id = id;
        this.type = type;
        this.model = model;
        this.number = number;
        this.mass = mass;
        this.age = age;
        this.run = run;
        this.color = color;
        this.engine = engine;
    }


    public double getCalcTaxPerMonth() {
        return mass * 0.0013 + (type.getTaxCoefficient() * engine.getTaxPerMonth() * 30) + 5;
    }

    @Override
    public String toString() {
        return String.format("%7s",id) + String.format("%10s",type.getTypeId()) + String.format("%10s",type.getTypeName()) + String.format("%30.20s",model)
                + String.format("%20s",number) + String.format("%10s",mass)  + String.format("%10s",age)
                + String.format("%10s",run) + String.format("%18.12s",color) + String.format("%50s",engine.toString())
                + String.format("%10s",profit) + String.format("%25s",getCalcTaxPerMonth())+  String.format("%25s",income) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return type.equals(vehicle.type) && model.equals(vehicle.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, model);
    }


}


