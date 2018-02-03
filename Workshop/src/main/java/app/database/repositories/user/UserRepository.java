package main.java.app.database.repositories.user;


import main.java.app.database.entities.User;
import main.java.app.database.entities.factories.UserFactory;
import main.java.app.database.enums.UserProperty;
import main.java.app.database.repositories.BaseRepository;

import javax.persistence.NoResultException;
import java.util.List;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class UserRepository extends BaseRepository {

    @Override
    public User getById(String id) {
        return this.findBy(UserProperty.ID, id);
    }

    @Override
    public boolean persist(String name, String pass) {
        entityManager.persist(UserFactory.create(name, pass));
        return true;
    }

    @Override
    public List<User> getAll() {
        String getAllNativeQuery = "SELECT * FROM users";
        return entityManager.createNativeQuery(getAllNativeQuery, User.class).getResultList();
    }

    @Override
    public boolean exists(String userEmail) {
        return this.findByEmail(userEmail) != null;
    }

    @Override
    public User findByEmail(String email) {
        return this.findBy(UserProperty.EMAIL, email);
    }

    private User findBy(UserProperty userProperty, String value) {
        switch (userProperty) {
            case ID:
                String getByIdNativeQuery = String.format("SELECT * FROM users u where u.email = '%s'", value);
                try {
                    return (User) entityManager.createNativeQuery(getByIdNativeQuery, User.class).getSingleResult();
                } catch (NoResultException nre) {
                    return null;
                }
            case EMAIL:
                String getByEmailNativeQuery = String.format("SELECT * FROM users u where u.email = '%s'", value);
                try {
                    return (User) entityManager.createNativeQuery(getByEmailNativeQuery, User.class).getSingleResult();
                } catch (NoResultException nre) {
                    return null;
                }
        }
        return null;
    }
}
