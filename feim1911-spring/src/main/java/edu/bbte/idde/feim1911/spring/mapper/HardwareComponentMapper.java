package edu.bbte.idde.feim1911.spring.mapper;

import edu.bbte.idde.feim1911.spring.dto.incoming.HardwareComponentInDto;
import edu.bbte.idde.feim1911.spring.dto.outgoing.HardwareComponentOutDto;
import edu.bbte.idde.feim1911.spring.model.HardwareComponent;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class HardwareComponentMapper {
    public abstract HardwareComponentOutDto modelToDto(HardwareComponent component);

    public abstract HardwareComponent dtoToModel(HardwareComponentInDto componentInDto);

    @IterableMapping(elementTargetType = HardwareComponentOutDto.class)
    public abstract Collection<HardwareComponentOutDto> modelsToDtos(Collection<HardwareComponent> components);

}
