package MainPackage.orm.entity;

import MainPackage.orm.annotations.Column;
import MainPackage.orm.annotations.ID;
import MainPackage.orm.annotations.Table;
import lombok.*;

@Table(name = "vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;
    @Column(name = "typeId")
    private Long typeId;
    @Column(name = "modelName")
    private String modelName;
    @Column(name = "registrationNumber", unique = true)
    private String registrationNumber;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "manufactureYear")
    private Integer manufactureYear;
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "color")
    private String color;
    @Column(name = "engineType")
    private String engineType;
    @Column(name = "taxCoefficient")
    private Double taxCoefficient;
    @Column(name = "engineCapacity")
    private Double engineCapacity;
    @Column(name = "fuelTankCapacity")
    private Double fuelTankCapacity;
    @Column(name = "fuelConsumptionPer100")
    private Double fuelConsumptionPer100;
}
