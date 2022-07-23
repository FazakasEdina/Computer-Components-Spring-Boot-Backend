package edu.bbte.idde.feim1911.spring.controller;

import edu.bbte.idde.feim1911.spring.controller.exception.NotFoundException;
import edu.bbte.idde.feim1911.spring.dao.HardwareComponentDao;
import edu.bbte.idde.feim1911.spring.dto.incoming.HardwareComponentInDto;
import edu.bbte.idde.feim1911.spring.dto.outgoing.HardwareComponentOutDto;
import edu.bbte.idde.feim1911.spring.mapper.HardwareComponentMapper;
import edu.bbte.idde.feim1911.spring.model.HardwareComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Collection;

@Controller
@RequestMapping("/api/computerComponents")
@Slf4j
public class HardwareComponentController {
    @Autowired
    HardwareComponentDao hardwareComponentDao;

    @Autowired
    HardwareComponentMapper hardwareComponentMapper;

    @GetMapping
    @ResponseBody
    public Collection<HardwareComponentOutDto> findAll(@RequestParam(name = "name", required = false) String name) {
        if (name != null) {
            return hardwareComponentMapper.modelsToDtos(hardwareComponentDao.findByName(name));
        }

        return hardwareComponentMapper.modelsToDtos(hardwareComponentDao.findAll());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public HardwareComponentOutDto findById(@PathVariable Long id) {
        HardwareComponent hardwareComponent = hardwareComponentDao.getById(id);
        if (hardwareComponent == null) {
            throw new NotFoundException();
        }

        hardwareComponent = hardwareComponentDao.getById(id);
        log.info("{}", hardwareComponent);
        hardwareComponentDao.save(hardwareComponent);
        return hardwareComponentMapper.modelToDto(hardwareComponent);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public HardwareComponentOutDto createEntity(@RequestBody @Valid HardwareComponentInDto entity) {

        HardwareComponent newEntity = hardwareComponentMapper.dtoToModel(entity);
        newEntity = hardwareComponentDao.save(newEntity);

        return hardwareComponentMapper.modelToDto(newEntity);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEntity(@PathVariable Long id,
                             @RequestBody @Valid HardwareComponentInDto entity) {
        HardwareComponent hardwareComponent = hardwareComponentDao.getById(id);

        if (hardwareComponent == null) {
            throw new NotFoundException();
        }

        HardwareComponent newEntity = hardwareComponentMapper.dtoToModel(entity);

        hardwareComponent.setName(newEntity.getName());
        hardwareComponent.setType(newEntity.getType());
        hardwareComponent.setAppearance(newEntity.getAppearance());
        hardwareComponent.setPrice(newEntity.getPrice());
        hardwareComponent.setInStock(newEntity.getInStock());
        hardwareComponent.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.info("{}", hardwareComponent);
        hardwareComponentDao.save(hardwareComponent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntity(@PathVariable Long id) {

        HardwareComponent hardwareComponent = hardwareComponentDao.getById(id);
        if (hardwareComponent == null) {
            throw new NotFoundException();
        }
        hardwareComponentDao.deleteById(id);
    }
}
