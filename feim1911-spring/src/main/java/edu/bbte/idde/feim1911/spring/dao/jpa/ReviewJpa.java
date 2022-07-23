package edu.bbte.idde.feim1911.spring.dao.jpa;

import edu.bbte.idde.feim1911.spring.dao.ReviewDao;
import edu.bbte.idde.feim1911.spring.model.Review;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface ReviewJpa extends ReviewDao, JpaRepository<Review, Long> {
}
