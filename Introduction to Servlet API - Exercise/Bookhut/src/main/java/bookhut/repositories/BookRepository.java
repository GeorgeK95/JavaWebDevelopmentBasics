package bookhut.repositories;

import bookhut.entities.Book;
import bookhut.repositories.contracts.IBookRepository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class BookRepository implements IBookRepository {

    private static BookRepository bookRepository;

    private Map<String, Book> books;

    private BookRepository() {
    }

    public static BookRepository getInstance() {
        if (bookRepository == null) {
            BookRepository.bookRepository = new BookRepository();
            BookRepository.bookRepository.setBooks();
        }

        return BookRepository.bookRepository;
    }

    private void setBooks() {
        this.books = new LinkedHashMap<>();
    }

    @Override
    public void saveBook(Book book) {
        this.books.put(book.getTitle(), book);
    }

    @Override
    public Map<String, Book> getAllBooks() {
        return this.books;
    }

    @Override
    public void deleteBookByTitle(String title) {
        this.books.remove(title);
    }

    @Override
    public Book findBookByTitle(String title) {
        return this.books.get(title);
    }

    @Override
    public void replaceBook(String bookOldTitle, Book book) {
        this.deleteBookByTitle(bookOldTitle);
        this.books.put(book.getTitle(), book);
    }
}
