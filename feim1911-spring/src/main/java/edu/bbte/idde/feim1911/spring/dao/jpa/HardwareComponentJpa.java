package edu.bbte.idde.feim1911.spring.dao.jpa;

import edu.bbte.idde.feim1911.spring.dao.HardwareComponentDao;
import edu.bbte.idde.feim1911.spring.model.HardwareComponent;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Repository
@Profile("jpa")
public interface  HardwareComponentJpa extends HardwareComponentDao, JpaRepository<HardwareComponent,Long> {

    @Query("UPDATE HardwareComponent SET lastUpdatedAt=:time WHERE id=:id")
    @Modifying
    @Transactional
    @Override
    int updateByLastViewedAt(@Param("time")Timestamp time, @Param("id")Long id);

    @Query("FROM HardwareComponent WHERE lastUpdatedAt < :date")
    @Override
    Collection<HardwareComponent> filterByLastUpdatedAt(@Param("date") Date date);
}
