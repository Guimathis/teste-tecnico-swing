package com.saam.testetecnicoswing.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author guimathis
 */
public interface Dao<T> {

    T insert(T entity);

    List<T> select();

    Optional<T> selectById(UUID id);

    void update(T entity);

    void delete(UUID id);
}
