package bookhut.controllers.crud;

import bookhut.models.bindingModels.factories.AddBookBindingModelFactory;
import bookhut.models.bindingModels.AddBookBindingModel;
import bookhut.models.bindingModels.LoginBindingModel;
import bookhut.services.contracts.IBookService;
import bookhut.services.factories.BookServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static bookhut.utils.Constants.*;

@WebServlet("/add")
public class AddBookController extends HttpServlet {
    private IBookService bookService;

    public AddBookController() {
        this.bookService = BookServiceFactory.create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBindingModel loginAttribute = (LoginBindingModel) req.getSession().getAttribute(LOGIN_MODEL);
        if (loginAttribute != null) {
            req.getRequestDispatcher(ADD_BOOK_SERVLET).forward(req, resp);
        } else {
            req.getRequestDispatcher(SIGN_IN_SERVLET).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBindingModel loginAttribute = (LoginBindingModel) req.getSession().getAttribute(LOGIN_MODEL);
        String add = req.getParameter(ADD);
        if (add != null && loginAttribute != null) {
            String title = req.getParameter(TITLE_LOW_CASE);
            String author = req.getParameter(AUTHOR_LOW_CASE);
            int pages = Integer.parseInt(req.getParameter(PAGES_LOW_CASE));

            AddBookBindingModel bookModel =  AddBookBindingModelFactory.create(title, author, pages);
            this.bookService.saveBook(bookModel);
            resp.sendRedirect(SHELVES_REDIRECT);
        }
    }
}
