package bookhut.controllers.crud;

import bookhut.models.bindingModels.AddBookBindingModel;
import bookhut.models.bindingModels.LoginBindingModel;
import bookhut.models.viewModels.BookViewModel;
import bookhut.services.contracts.IBookService;
import bookhut.services.factories.BookServiceFactory;
import bookhut.utils.DTOConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

import static bookhut.utils.Constants.*;

@WebServlet("/shelves/edit/*")
public class EditBookController extends HttpServlet {

    private IBookService bookService;

    public EditBookController() {
        this.bookService = BookServiceFactory.create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tokens[] = req.getRequestURI().split(SLASH);
        String title = URLDecoder.decode(tokens[4], UTF_8);
        BookViewModel viewBookModel = this.bookService.findBook(title);
        LoginBindingModel loginBindingModel = (LoginBindingModel) req.getSession().getAttribute(LOGIN_MODEL);
        if (viewBookModel != null && loginBindingModel != null) {
            req.setAttribute(TITLE_UP_CASE, viewBookModel.getTitle());
            req.setAttribute(AUTHOR_UP_CASE, viewBookModel.getAuthor());
            req.setAttribute(PAGES_UP_CASE, viewBookModel.getPages());
            req.getRequestDispatcher(EDIT_SERVLET).forward(req, resp);
        } else {
            req.getRequestDispatcher(SIGN_IN_SERVLET).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String edit = req.getParameter(EDIT);
        LoginBindingModel loginBindingModel = (LoginBindingModel) req.getSession().getAttribute(LOGIN_MODEL);
        if (edit != null && loginBindingModel != null) {
            String originalTitle = req.getParameter(OLD_TITLE_LOW_CASE);
            String title = req.getParameter(TITLE_LOW_CASE);
            String author = req.getParameter(AUTHOR_LOW_CASE);
            int pages = Integer.parseInt(req.getParameter(PAGES_LOW_CASE));

            AddBookBindingModel edited = this.editBook(originalTitle, title, author, pages);

            this.bookService.replaceBook(originalTitle, edited);
            resp.sendRedirect(SHELVES_REDIRECT);
        }
    }

    private AddBookBindingModel editBook(String originalTitle, String title, String author, int pages) {
        BookViewModel bookViewModel = this.bookService.findBook(originalTitle);
        AddBookBindingModel bookToEdit = DTOConverter.convert(bookViewModel, AddBookBindingModel.class);
        bookToEdit.setAuthor(author);
        bookToEdit.setTitle(title);
        bookToEdit.setPages(pages);

        return bookToEdit;
    }
}
