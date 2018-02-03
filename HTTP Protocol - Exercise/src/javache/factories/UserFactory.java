package javache.factories;

import javache.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javache.utils.WebConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class UserFactory {
    private UserFactory() {
    }

    public static User create(String id, String name, String pass) {
        return UserFactory.create(new HashMap<String, String>() {
            {
                put(ID, id);
                put(EMAIL, name);
                put(PASSWORD, pass);
            }
        });
    }

    public static User create(Map<String, String> userData) {
        String id = null;
        if (userData.containsKey(ID)) {
            id = userData.get(ID);
        } else {
            id = UUID.randomUUID().toString();
        }
        String username = userData.get(EMAIL);
        String password = userData.get(PASSWORD);
        String confirm = userData.get(CONFIRM);
        User user = new User(id, username, password);
        user.setConfirm(confirm);
        return user;
    }
}
