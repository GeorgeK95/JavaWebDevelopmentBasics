package bookhut.repositories.contracts;

import bookhut.entities.Book;

import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IBookRepository {
    void saveBook(Book book);

    Map<String, Book> getAllBooks();

    void deleteBookByTitle(String title);

    Book findBookByTitle(String title);

    void replaceBook(String bookOldTitle, Book book);
}
