package edu.bbte.idde.feim1911.spring.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rev")
public class Review extends BaseEntity {
    private String description;
    private Integer stars;

    // private Long componentId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private HardwareComponent component;
}
