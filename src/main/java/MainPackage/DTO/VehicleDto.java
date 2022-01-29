package MainPackage.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class VehicleDto {

    private int id;

    private int typeId;

    private String typeName;

    private double taxCoefficient;

    private String modelName;

    private int manufactureYear;

    private String registrationNumber;

    private double weight;

    private int mileage;

    private String color;

    private double tankVolume;

    private String engineName;

    private double engineTaxCoefficient;

    private double per100km;

    private double maxKm;

    private double tax;

    private double income;

}
