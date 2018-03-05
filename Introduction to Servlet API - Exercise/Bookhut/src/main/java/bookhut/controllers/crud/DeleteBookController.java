package bookhut.controllers.crud;

import bookhut.models.bindingModels.LoginBindingModel;
import bookhut.services.contracts.IBookService;
import bookhut.services.factories.BookServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

import static bookhut.utils.Constants.*;

@WebServlet("/shelves/delete/*")
public class DeleteBookController extends HttpServlet {

    private IBookService bookService;

    public DeleteBookController() {
        this.bookService = BookServiceFactory.create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBindingModel loginBindingModel = (LoginBindingModel) req.getSession().getAttribute(LOGIN_MODEL);
        if (loginBindingModel != null) {
            String tokens[] = req.getRequestURI().split(SLASH);
            String title = URLDecoder.decode(tokens[4], UTF_8);
            this.bookService.deleteBook(title);
        }
        resp.sendRedirect(SHELVES_REDIRECT);
    }
}
