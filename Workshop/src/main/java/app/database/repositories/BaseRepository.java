package main.java.app.database.repositories;

import main.java.app.database.entities.User;
import main.java.app.database.repositories.user.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public abstract class BaseRepository implements IUserRepository<User> {
    private static final String CASEBOOK_PERSISTENCE_UNIT = "CasebookUnit";
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(CASEBOOK_PERSISTENCE_UNIT);

    protected EntityManager entityManager;

    protected Map<String, Method> methodMap;

    protected BaseRepository() {
        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        this.initMethods();
    }

    private void initMethods() {
        this.methodMap = new HashMap<>();

        for (Method method : this.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            this.methodMap.putIfAbsent(method.getName(), method);
        }
    }

    @Override
    public Object invokeMethod(String method, Object... params) {
        EntityTransaction transaction = null;
        Object invokedMethodResult = null;
        try {
            transaction = this.entityManager.getTransaction();
            transaction.begin();
            invokedMethodResult = this.methodMap.get(method).invoke(this, params);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return invokedMethodResult;
    }

    @Override
    public void dismiss() {
        this.entityManager.close();
        ENTITY_MANAGER_FACTORY.close();
    }
}
