package edu.bbte.idde.feim1911.spring.dao;

import edu.bbte.idde.feim1911.spring.model.HardwareComponent;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

public interface HardwareComponentDao extends Dao<HardwareComponent> {
    Collection<HardwareComponent> findByName(String name);

    int updateByLastViewedAt(Timestamp time, Long id);

    Collection<HardwareComponent> filterByLastUpdatedAt(Date date);
}
