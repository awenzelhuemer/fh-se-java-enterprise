package swt6.dal.dao.impl;

import swt6.dal.dao.BaseDao;
import swt6.dal.domain.BaseEntity;
import swt6.dal.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public abstract class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    @Override
    public T getById(long id) {
        return null;
    }

    @Override
    public T insert(T entity) {
        EntityManager em = JpaUtil.getTransactedEntityManager();

        if (entity.getId() != 0) {
            throw new IllegalArgumentException("Id of entity is not 0.");
        }

        return em.merge(entity);
    }

    @Override
    public T update(T entity) {
        T storedEntity = null;
        EntityManager em = JpaUtil.getTransactedEntityManager();

        if (entity.getId() == 0) {
            throw new IllegalArgumentException("Id needs to be specified.");
        }

        storedEntity = em.find(getEntityType(), entity.getId());
        if (storedEntity == null) {
            throw new RuntimeException("Entity does not exist.");
        }

        storedEntity = em.merge(entity);

        return storedEntity;
    }

    @Override
    public boolean delete(T entity) {
        EntityManager em = JpaUtil.getTransactedEntityManager();

        entity = em.merge(entity);

        deleteDependencies();

        em.remove(entity);

        return true;
    }

    protected abstract Class<T> getEntityType();

    protected void deleteDependencies() {
        // No dependencies to delete
    }
}
