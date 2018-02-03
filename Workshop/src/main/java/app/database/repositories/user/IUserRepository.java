package main.java.app.database.repositories.user;

import main.java.app.database.repositories.DbRepository;

import java.util.List;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IUserRepository<User> extends DbRepository<User> {
    User getById(String id);

    boolean persist(String name, String pass);

    List<User> getAll();

    boolean exists(String email);

    User findByEmail(String user);

    Object invokeMethod(String method, Object... params);
}
