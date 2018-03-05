package bookhut.repositories.contracts;

import bookhut.entities.User;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IUserRepository {
    void createUser(User user);

    User findByUsernameAndPassword(String username, String password);

    boolean exist(String username);
}
