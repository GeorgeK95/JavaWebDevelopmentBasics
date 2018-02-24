package repositories;

import entities.User;

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
    public User[] findAll() {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        User user = (User) super.execute(actionResult -> {
            String findByEmailQuery = String.format("SELECT * FROM users u WHERE u.email = '%s'", email);

            Object queryResult = super.getQueryResult(findByEmailQuery, User.class);

            actionResult.setResult(queryResult);
        }).getResult();

        return user;
    }

    @Override
    public void delete(String id) {
    }
}
