package bookhut.models.bindingModels.factories;

import bookhut.models.bindingModels.LoginBindingModel;

/**
 * Created by George-Lenovo on 02/17/2018.
 */
public class LoginBindingModelFactory {

    public static LoginBindingModel create(String userName, String password) {
        return new LoginBindingModel(userName, password);
    }
}
