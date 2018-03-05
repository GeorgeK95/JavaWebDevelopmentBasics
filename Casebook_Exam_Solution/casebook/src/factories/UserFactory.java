package factories;

import bindingModels.UserRegisterBindingModel;
import entities.Gender;
import entities.User;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public final class UserFactory {
    public static User create(UserRegisterBindingModel userRegisterBindingModel) {
        User user = new User();
        user.setUsername(userRegisterBindingModel.getUsername());
        user.setGender(Gender.valueOf(userRegisterBindingModel.getGender().toUpperCase()));
        user.setPassword(userRegisterBindingModel.getPassword());
        return user;
    }

    private UserFactory() {
    }
}