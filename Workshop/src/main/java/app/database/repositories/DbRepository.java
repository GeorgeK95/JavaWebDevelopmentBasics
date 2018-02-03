package main.java.app.database.repositories;

import java.util.List;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface DbRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean persist(String name, String pass);

    Object invokeMethod(String method, Object... params);

    boolean exists(String columnCriteria);

    void dismiss();
}
