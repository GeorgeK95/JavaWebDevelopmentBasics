package bookhut.services.factories;

import bookhut.services.contracts.IUserService;
import bookhut.services.UserService;

/**
 * Created by George-Lenovo on 02/17/2018.
 */
public class UserServiceFactory {

    public static IUserService create() {
        return new UserService();
    }
}
