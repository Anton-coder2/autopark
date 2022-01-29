package MainPackage.orm.entity;

import MainPackage.orm.annotations.Column;
import MainPackage.orm.annotations.ID;
import MainPackage.orm.annotations.Table;
import lombok.*;

import java.util.Date;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID
    private Long id;
    @Column(name = "rentDate")
    private Date rentDate;
    @Column(name = "cost")
    private Double cost;
    @Column(name = "vehicleId")
    private Long vehicleId;
}
