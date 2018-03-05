package bookhut.repositories.factories;

import bookhut.repositories.BookRepository;
import bookhut.repositories.UserRepository;

/**
 * Created by George-Lenovo on 02/15/2018.
 */
public class BookRepositoryFactory extends AbstractRepoFactory {

    @Override
    public UserRepository getUserRepository() {
        return null;
    }

    @Override
    public BookRepository getBookRepository() {
        return BookRepository.getInstance();
    }
}
