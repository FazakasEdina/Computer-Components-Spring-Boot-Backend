package edu.bbte.idde.feim1911.spring.dao.mem;

import edu.bbte.idde.feim1911.spring.dao.ReviewDao;
import edu.bbte.idde.feim1911.spring.model.Review;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Profile("mem")
public class ReviewInMemDao implements ReviewDao {
    private final ConcurrentHashMap<Long, Review> entities;
    private final AtomicLong lastId;

    public ReviewInMemDao() {
        entities = new ConcurrentHashMap<>();
        lastId = new AtomicLong(1L);
    }

    @Override
    public Review save(Review entity) {
        entity.setId(lastId.getAndIncrement());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<Review> findAll() {
        return entities.values();
    }

    @Override
    public Review getById(Long id) {
        return entities.get(id);
    }

    @Override
    public void deleteById(Long id) {
        if (entities.containsKey(id)) {
            entities.remove(id);
        }
    }

    @Override
    public Collection<Review> findByStars(Integer count) {
        return entities.values()
                .stream()
                .filter(e -> e.getStars().equals(count))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Boolean updateEntity(Long id, Review entity) {
        // if exists
        if (entities.containsKey(id)) {
            entities.replace(id, entity);
            entity.setId(id);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Review> findByComponentId(Long id) {
        return entities.values()
                .stream()
                //.filter(e -> e.getComponentId().equals(id))
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
