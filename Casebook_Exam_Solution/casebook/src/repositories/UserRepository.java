package repositories;

import entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public class UserRepository extends BaseRepository implements Repository<User> {

    @Override
    public void create(User user) {
        super.execute(actionResult -> {
            super.entityManager.persist(user);
        });
    }

    @Override
    public User findById(String id) {
        User user = (User) super.execute(actionResult -> {
            String findByIdQuery = String.format("SELECT * FROM users u WHERE u.id = '%s'", id);

            Object queryResult = super.getQueryResult(findByIdQuery, User.class);

            actionResult.setResult(queryResult);
        }).getResult();

        return user;
    }

    @Override
    public User findByName(String name) {
        User user = (User) super.execute(actionResult -> {
            String findByUsernameQuery = String.format("SELECT * FROM users u WHERE u.username = '%s'", name);

            Object queryResult = super.getQueryResult(findByUsernameQuery, User.class);

            actionResult.setResult(queryResult);
        }).getResult();

        return user;
    }

    @Override
    public User[] findAll(String currentLoggedUserId) {
        String findAllUsersQuery = String.format("SELECT * FROM users as u WHERE u.id != '%s'", currentLoggedUserId);
        List<User> resultList = new ArrayList<>();
        super.execute(actionResult -> {
            resultList.addAll(super.getResults(findAllUsersQuery, User.class));
        });

        return resultList.toArray(new User[resultList.size()]);
    }

    @Override
    public void updateUser(User user) {
        super.execute(actionResult -> {
            super.entityManager.merge(user);
        });
    }

}
