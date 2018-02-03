package javache.repositories;

import java.util.Set;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IUserRepository<User> extends Repository<User> {
    User getById(String id);

    void persist(User user);

    Set<User> getAll();

    boolean exists(User email);

    User findByEmail(String user);
}
