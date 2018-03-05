package bookhut.repositories;

import bookhut.entities.User;
import bookhut.repositories.contracts.IUserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class UserRepository implements IUserRepository {
    private static UserRepository userRepository;

    private Map<String, User> users;

    private UserRepository() {
    }

    private void setUsers() {
        this.users = new HashMap<>();
    }

    @Override
    public void createUser(User user) {
        this.users.put(user.getUserName(), user);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User targetUser = this.users.get(username);
        if (targetUser != null && targetUser.getPassword().equals(password)) return targetUser;
        return null;
    }

    @Override
    public boolean exist(String username) {
        return this.users.containsKey(username);
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            UserRepository.userRepository = new UserRepository();
            UserRepository.userRepository.setUsers();
        }

        return UserRepository.userRepository;
    }
}
