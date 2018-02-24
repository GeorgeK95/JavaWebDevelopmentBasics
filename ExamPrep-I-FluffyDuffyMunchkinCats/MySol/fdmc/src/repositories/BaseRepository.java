package repositories;

import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public abstract class BaseRepository {
    private static final String TRANSACTION_IS_ACTIVE_MESSAGE = "Transaction is active.";
    private static final String TRANSACTION_IS_NOT_INSTANTIATED_OR_IS_INACTIVE_MESSAGE = "Transaction is not instantiated or is inactive.";
    private static final String CASEBOOK_PERSISTENCE_UNIT = "fdmc";

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(CASEBOOK_PERSISTENCE_UNIT);
    private EntityTransaction transaction;

    protected EntityManager entityManager;

    protected BaseRepository() {
    }

    private void initEntityManager() {
        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    private void initTransaction() {
        if (this.transaction != null && this.transaction.isActive()) {
            throw new IllegalArgumentException(TRANSACTION_IS_ACTIVE_MESSAGE);
        }
        this.transaction = this.entityManager.getTransaction();
        this.transaction.begin();
    }

    private void commitTransaction() {
        if (this.transaction == null || !this.transaction.isActive()) {
            throw new IllegalArgumentException(TRANSACTION_IS_NOT_INSTANTIATED_OR_IS_INACTIVE_MESSAGE);
        }

        this.transaction.commit();
    }

    private void dismiss() {
        this.entityManager.close();
//        ENTITY_MANAGER_FACTORY.close();//TODO:potential bug
    }

    protected RepositoryActionResult execute(RepositoryInvoker invoker) {
        RepositoryActionResult result = new RepositoryActionResult(null);

        try {
            this.initEntityManager();
            this.initTransaction();
            invoker.invoke(result);
            this.commitTransaction();
            this.dismiss();
        } catch (Exception e) {
            if (this.transaction != null) this.transaction.rollback();
            e.printStackTrace();
        }

        return result;
    }

    protected Object getQueryResult(String query) {
        return this.entityManager.createNativeQuery(query, User.class)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}