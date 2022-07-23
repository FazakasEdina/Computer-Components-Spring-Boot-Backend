package edu.bbte.idde.feim1911.spring.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "computerComponent")
public class HardwareComponent extends BaseEntity {
    private String name;
    private String type;
    private Date appearance;
    private Float price;
    private Integer inStock;

    private Timestamp lastViewedAt;
    private Timestamp lastUpdatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}
