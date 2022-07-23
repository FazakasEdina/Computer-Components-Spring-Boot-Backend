package edu.bbte.idde.feim1911.spring.dto.incoming;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class HardwareComponentInDto {
    @NotNull
    @NotEmpty
    @Size(max = 30)
    private String name;
    @NotNull
    @NotEmpty
    @Size(max = 30)
    private String type;
    @NotNull
    @PastOrPresent
    private Date appearance;
    @NotNull
    @Positive
    private Float price;
    @NotNull
    @Positive
    private Integer inStock;
}
