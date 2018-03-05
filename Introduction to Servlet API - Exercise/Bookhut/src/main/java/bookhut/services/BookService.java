package bookhut.services;

import bookhut.entities.Book;
import bookhut.models.bindingModels.AddBookBindingModel;
import bookhut.models.viewModels.BookViewModel;
import bookhut.repositories.contracts.IBookRepository;
import bookhut.repositories.factories.AbstractRepoFactory;
import bookhut.repositories.factories.FactoryProducer;
import bookhut.services.contracts.IBookService;
import bookhut.utils.DTOConverter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static bookhut.utils.Constants.BOOK;

/**
 * Created by George-Lenovo on 02/15/2018.
 */
public class BookService implements IBookService {
    private IBookRepository bookRepository;

    public BookService() {
        AbstractRepoFactory bookFactory = FactoryProducer.getFactory(BOOK);
        this.bookRepository = bookFactory.getBookRepository();
    }

    @Override
    public void saveBook(AddBookBindingModel bookModel) {
        Book book = DTOConverter.convert(bookModel, Book.class);
        this.bookRepository.saveBook(book);
    }

    @Override
    public void replaceBook(String bookOldTitle, AddBookBindingModel bookBindingModel) {
        Book book = DTOConverter.convert(bookBindingModel, Book.class);
        this.bookRepository.replaceBook(bookOldTitle, book);
    }

    @Override
    public Set<BookViewModel> getAllBooks() {
        return DTOConverter.convert(this.bookRepository.getAllBooks().values(), BookViewModel.class)
                .stream().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public BookViewModel findBook(String title) {
        Book bookByTitle = this.bookRepository.findBookByTitle(title);
        return DTOConverter.convert(bookByTitle, BookViewModel.class);
    }

    @Override
    public void deleteBook(String title) {
        this.bookRepository.deleteBookByTitle(title);
    }
}
