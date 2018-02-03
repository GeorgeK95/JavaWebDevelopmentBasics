package main.java.app.database.entities.factories;


import main.java.app.database.entities.User;
import main.java.app.database.enums.UserProperty;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class UserFactory {
    private UserFactory() {
    }

    public static User create(String name, String pass) {
        return UserFactory.create(new HashMap<String, String>() {
            {
                put(UserProperty.EMAIL.name(), name);
                put(UserProperty.PASSWORD.name(), pass);
            }
        });
    }

    private static User create(Map<String, String> userData) {
        String username = userData.get(UserProperty.EMAIL.name());
        String password = userData.get(UserProperty.PASSWORD.name());
        String confirm = userData.get(UserProperty.CONFIRM.name());
        User user = new User(username, password);
        user.setConfirm(confirm);
        return user;
    }
}
