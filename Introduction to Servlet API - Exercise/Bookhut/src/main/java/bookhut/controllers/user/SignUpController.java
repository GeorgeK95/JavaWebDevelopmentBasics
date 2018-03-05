package bookhut.controllers.user;

import bookhut.models.bindingModels.LoginBindingModel;
import bookhut.models.bindingModels.factories.LoginBindingModelFactory;
import bookhut.services.contracts.IUserService;
import bookhut.services.factories.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static bookhut.utils.Constants.*;

/**
 * Created by George-Lenovo on 02/15/2018.
 */
@WebServlet("/signup")
public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SIGN_UP_SERVLET).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBindingModel loginModel = null;
        String signUp = req.getParameter(SIGN_UP);
        if (signUp != null) {
            String username = req.getParameter(USERNAME);
            String password = req.getParameter(PASSWORD);
            IUserService userService = UserServiceFactory.create();
            if (username != null && password != null && !userService.exist(username)) {
                loginModel = LoginBindingModelFactory.create(username, password);
                userService.createUser(loginModel);
                resp.sendRedirect(SIGN_IN_REDIRECT);
            }
        }
        if (loginModel == null) {
            resp.sendRedirect(SIGN_UP_REDIRECT);
        }
    }
}
