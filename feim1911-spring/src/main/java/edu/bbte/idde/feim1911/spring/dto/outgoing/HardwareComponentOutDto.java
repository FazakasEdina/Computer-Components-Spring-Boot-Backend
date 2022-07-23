package edu.bbte.idde.feim1911.spring.dto.outgoing;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class HardwareComponentOutDto {
    private Long id;
    private String name;
    private String type;
    private Date appearance;
    private Float price;
    private Integer inStock;
    private Timestamp lastViewedAt;
    private Timestamp lastUpdateAt;
}
