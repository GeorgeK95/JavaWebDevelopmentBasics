package repositories;

import entities.JobApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public abstract class BaseRepository {
    private static final String TRANSACTION_IS_ACTIVE_MESSAGE = "Transaction is active.";
    private static final String TRANSACTION_IS_NOT_INSTANTIATED_OR_IS_INACTIVE_MESSAGE = "Transaction is not instantiated or is inactive.";
    private static final String CASEBOOK_PERSISTENCE_UNIT = "sboj";

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

    protected Object getQueryResult(String query, Class<?> clazz) {
        return this.entityManager.createNativeQuery(query, clazz)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    protected void executeDmlStatementQuery(String query) {
        this.entityManager.createNativeQuery(query)
                .executeUpdate();
    }


    protected List<JobApplication> getResults(String findAllJobsQuery, Class<JobApplication> jobApplicationClass) {
        return this.entityManager.createNativeQuery(findAllJobsQuery, jobApplicationClass).getResultList();
    }
}