package javache.repositories;

import java.util.Set;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface Repository<T> {
    T getById(String id);

    void persist(T object);

    Set<T> getAll();

    boolean exists(T user);

}
