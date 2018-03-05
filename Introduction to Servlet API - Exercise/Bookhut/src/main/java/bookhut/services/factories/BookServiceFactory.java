package bookhut.services.factories;

import bookhut.services.BookService;

/**
 * Created by George-Lenovo on 02/17/2018.
 */
public class BookServiceFactory {
    public static BookService create() {
        return new BookService();
    }

}
