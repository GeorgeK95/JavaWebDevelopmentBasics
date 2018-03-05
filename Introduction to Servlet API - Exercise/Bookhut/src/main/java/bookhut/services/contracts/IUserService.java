package bookhut.services.contracts;

import bookhut.models.bindingModels.LoginBindingModel;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IUserService {
    void createUser(LoginBindingModel loginModel);

    LoginBindingModel findUser(String username, String password);

    boolean exist(String username);
}
