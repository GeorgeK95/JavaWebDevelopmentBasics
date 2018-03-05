package bookhut.services.contracts;

import bookhut.models.bindingModels.AddBookBindingModel;
import bookhut.models.viewModels.BookViewModel;

import java.util.Set;

public interface IBookService {

    void saveBook(AddBookBindingModel addBookModel);

    void replaceBook(String bookOldTitle, AddBookBindingModel book);

    Set<BookViewModel> getAllBooks();

    BookViewModel findBook(String title);

    void deleteBook(String title);
}
