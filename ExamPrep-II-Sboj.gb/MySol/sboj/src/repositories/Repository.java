package repositories;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public interface Repository<T> {
    void create(T obj);

    T findById(String id);

    T findByName(String name);

    T[] findAll();

    T findByEmail(String email);

    void delete(String id);
}
