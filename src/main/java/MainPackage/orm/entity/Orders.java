package MainPackage.orm.entity;

import MainPackage.orm.annotations.Column;
import MainPackage.orm.annotations.ID;
import MainPackage.orm.annotations.Table;
import lombok.*;

@Table(name = "orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @ID
    public Long id;
    @Column(name = "vehicleID")
    public Long vehicleID;
    @Column(name = "detail")
    public String detail;
    @Column(name = "countOfDetails")
    public Integer countOfDetails;
}
