package MainPackage.DTO;

import MainPackage.checkLocation.VehicleCollection;
import MainPackage.core.annotations.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class serviceDTO {
    @Autowired
    VehicleCollection vehicleCollection;

    public List<VehicleDto> getVehicles(){
        return vehicleCollection.getVehicles().stream().map(vehicle -> {
            return VehicleDto.builder()
                    .id(vehicle.getId())
                    .typeId(vehicle.getType().getTypeId())
                    .typeName(vehicle.getType().getTypeName())
                    .taxCoefficient(vehicle.getType().getTaxCoefficient())
                    .color(vehicle.getColor().name())
                    .engineName(vehicle.getEngine().getEngineName())
                    .engineTaxCoefficient(vehicle.getEngine().getTaxPerMonth())
                    .tax(vehicle.getCalcTaxPerMonth())
                    .manufactureYear(vehicle.getAge())
                    .mileage(vehicle.getRun())
                    .modelName(vehicle.getModel())
                    .registrationNumber(vehicle.getNumber())
                    .tankVolume(vehicle.getTankVolume())
                    .weight(vehicle.getMass())
                    .per100km(vehicle.getEngine().getFuelPer100km())
                    .maxKm(vehicle.getEngine().getMaxKilometrs())
                    .income(vehicle.getIncome())
                    .build();
        }).collect(Collectors.toList());
    }
}
