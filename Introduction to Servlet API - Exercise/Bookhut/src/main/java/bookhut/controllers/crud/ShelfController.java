package bookhut.controllers.crud;

import bookhut.models.viewModels.BookViewModel;
import bookhut.services.contracts.IBookService;
import bookhut.services.factories.BookServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static bookhut.utils.Constants.ALL_BOOKS;
import static bookhut.utils.Constants.SHELVES_SERVLET;

@WebServlet("/shelves")
public class ShelfController extends HttpServlet {

    private IBookService bookService;

    public ShelfController() {
        this.bookService = BookServiceFactory.create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<BookViewModel> allBooks = this.bookService.getAllBooks();
        req.setAttribute(ALL_BOOKS, allBooks);
        req.getRequestDispatcher(SHELVES_SERVLET).forward(req, resp);
    }
}
