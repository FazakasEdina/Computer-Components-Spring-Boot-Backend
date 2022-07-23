package edu.bbte.idde.feim1911.spring.mapper;

import edu.bbte.idde.feim1911.spring.dto.incoming.ReviewInDto;
import edu.bbte.idde.feim1911.spring.dto.outgoing.ReviewOutDto;
import edu.bbte.idde.feim1911.spring.model.Review;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
    public abstract ReviewOutDto modelToDto(Review entity);

    public abstract Review dtoToModel(ReviewInDto entityInDto);

    @IterableMapping(elementTargetType = ReviewOutDto.class)
    public abstract Collection<ReviewOutDto> modelsToDtos(Collection<Review> entities);
}
