package edu.bbte.idde.feim1911.spring.dto.outgoing;

import lombok.Data;

@Data
public class ReviewOutDto {
    private Long id;
    private String description;
    private Integer stars;
    private HardwareComponentOutDto component;
}
