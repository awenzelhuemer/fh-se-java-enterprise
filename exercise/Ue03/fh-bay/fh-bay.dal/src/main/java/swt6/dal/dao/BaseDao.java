package swt6.dal.dao;

import swt6.dal.domain.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends BaseEntity> {
    Optional<T> findById(long id);
    T insert(T entity);
    T update(T entity);
    List<T> findAll();
    void delete(long id);
}
