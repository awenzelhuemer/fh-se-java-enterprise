package swt6.dal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory emFactory;
    private static final ThreadLocal<EntityManager> emThread = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(JpaUtil.class);

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("FhBayPU");
            logger.info("EntityManagerFactory initialized.");
        }
        return emFactory;
    }

    public static synchronized EntityManager getEntityManager() {
        if (emThread.get() == null) {
            emThread.set(getEntityManagerFactory().createEntityManager());
        }
        return emThread.get();
    }

    public static synchronized void closeEntityManager() {
        if (emThread.get() != null) {
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (!tx.isActive()) {
            logger.info("New transaction ...");
            tx.begin();
        }

        return em;
    }

    public static synchronized void commit() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive()) {
            logger.info("Transaction completed ...");
            tx.commit();
        }
        closeEntityManager();
    }

    public static synchronized void rollback() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive()) {
            logger.info("Transaction rolled back ...");
            tx.rollback();
        }
        closeEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }
}
