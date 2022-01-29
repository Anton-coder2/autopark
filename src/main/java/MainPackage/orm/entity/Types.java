package MainPackage.orm.entity;

import MainPackage.orm.annotations.Column;
import MainPackage.orm.annotations.ID;
import MainPackage.orm.annotations.Table;
import lombok.*;

@Table(name = "types")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Types {
    @ID
    public Long id;
    @Column(name = "name", unique = true)
    public String name;
    @Column(name = "coefTaxes")
    public Double coefTaxes;
}
