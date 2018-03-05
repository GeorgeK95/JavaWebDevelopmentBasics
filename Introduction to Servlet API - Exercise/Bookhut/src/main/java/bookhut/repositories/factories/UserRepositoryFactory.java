package bookhut.repositories.factories;

import bookhut.repositories.BookRepository;
import bookhut.repositories.UserRepository;

/**
 * Created by George-Lenovo on 02/15/2018.
 */
public class UserRepositoryFactory extends AbstractRepoFactory {

    @Override
    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

    @Override
    public BookRepository getBookRepository() {
        return null;
    }
}
