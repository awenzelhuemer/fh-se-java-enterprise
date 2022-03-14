package swt6.dal.dao.impl;

import swt6.dal.dao.BaseDao;
import swt6.dal.domain.BaseEntity;
import swt6.dal.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    @Override
    public T findById(long id) {
        EntityManager em = JpaUtil.getTransactedEntityManager();
        return em.find(getEntityType(), id);
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
        EntityManager em = JpaUtil.getTransactedEntityManager();

        if (entity.getId() == 0) {
            throw new IllegalArgumentException("Id needs to be specified.");
        }

        T storedEntity = em.find(getEntityType(), entity.getId());
        if (storedEntity == null) {
            throw new RuntimeException("Entity does not exist.");
        }

        storedEntity = em.merge(entity);

        return storedEntity;
    }

    @Override
    public void delete(long id) {
        EntityManager em = JpaUtil.getTransactedEntityManager();

        T storedEntity = em.find(getEntityType(), id);
        if (storedEntity == null) {
            throw new RuntimeException("Entity does not exist.");
        }

        em.remove(storedEntity);
    }

    @Override
    public List<T> findAll() {
        EntityManager em = JpaUtil.getTransactedEntityManager();

        return em.createQuery( "from " + getEntityType().getSimpleName(), getEntityType()).getResultList();
    }

    protected abstract Class<T> getEntityType();
}
