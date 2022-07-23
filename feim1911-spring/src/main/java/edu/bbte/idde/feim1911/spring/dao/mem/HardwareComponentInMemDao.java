package edu.bbte.idde.feim1911.spring.dao.mem;

import edu.bbte.idde.feim1911.spring.dao.HardwareComponentDao;
import edu.bbte.idde.feim1911.spring.model.HardwareComponent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Profile("mem")
public class HardwareComponentInMemDao implements HardwareComponentDao {
    private final ConcurrentHashMap<Long, HardwareComponent> entities;
    private final AtomicLong lastId;

    public HardwareComponentInMemDao() {
        entities = new ConcurrentHashMap<>();
        lastId = new AtomicLong(1L);
    }

    @Override
    public HardwareComponent save(HardwareComponent entity) {
        entity.setId(lastId.getAndIncrement());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<HardwareComponent> findAll() {
        return entities.values();
    }

    @Override
    public HardwareComponent getById(Long id) {
        return entities.get(id);
    }

    @Override
    public void deleteById(Long id) {
        if (entities.containsKey(id)) {
            entities.remove(id);
        }
    }

    @Override
    public Collection<HardwareComponent> findByName(String name) {
        return entities.values()
                .stream()
                .filter(e -> e.getName().equals(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int updateByLastViewedAt(Timestamp time, Long id) {
        return 0;
    }

    @Override
    public Collection<HardwareComponent> filterByLastUpdatedAt(Date date) {
        return Collections.emptyList();
    }

    public Boolean updateEntity(Long id, HardwareComponent entity) {
        // if exists
        if (entities.containsKey(id)) {
            entities.replace(id, entity);
            entity.setId(id);
            return true;
        }
        return false;
    }

}
