package edu.bbte.idde.feim1911.spring.dao;

import edu.bbte.idde.feim1911.spring.model.Review;

import java.util.Collection;

public interface ReviewDao extends Dao<Review> {
    Collection<Review> findByStars(Integer count);

    Collection<Review> findByComponentId(Long id);
}
