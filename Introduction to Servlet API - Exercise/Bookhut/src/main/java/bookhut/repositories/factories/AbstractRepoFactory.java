package bookhut.repositories.factories;

import bookhut.repositories.BookRepository;
import bookhut.repositories.UserRepository;

/**
 * Created by George-Lenovo on 02/15/2018.
 */
public abstract class AbstractRepoFactory {
    public abstract UserRepository getUserRepository();

    public abstract BookRepository getBookRepository();
}
