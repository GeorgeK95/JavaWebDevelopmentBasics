package factories;

import repositories.UserRepository;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public final class UserRepositoryFactory {
    public static UserRepository create() {
        return new UserRepository();
    }

    private UserRepositoryFactory() {
    }
}
