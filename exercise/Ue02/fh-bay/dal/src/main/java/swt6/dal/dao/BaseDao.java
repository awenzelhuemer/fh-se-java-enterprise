package swt6.dal.dao;

import swt6.dal.domain.BaseEntity;

public interface BaseDao<T extends BaseEntity> {
    public T getById(long id);
    public T insert(T entity);
    public T update(T entity);
    public boolean delete(T entity);
}
