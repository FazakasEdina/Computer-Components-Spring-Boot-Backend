package edu.bbte.idde.feim1911.spring.dto.incoming;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ReviewInDto {
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String description;
    @NotNull
    @Range(min = 0, max = 10)
    private Integer stars;
}
