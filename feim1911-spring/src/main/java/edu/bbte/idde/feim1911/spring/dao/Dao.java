package edu.bbte.idde.feim1911.spring.dao;

import java.util.Collection;

public interface Dao<T> {
    T save(T entity);

    Collection<T> findAll();

    T getById(Long id);

    void deleteById(Long id);
}
