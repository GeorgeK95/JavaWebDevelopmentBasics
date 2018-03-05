package bookhut.controllers.user;

import bookhut.models.bindingModels.LoginBindingModel;
import bookhut.services.contracts.IUserService;
import bookhut.services.factories.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static bookhut.utils.Constants.*;

/**
 * Created by George-Lenovo on 02/15/2018.
 */
@WebServlet("/signin")
public class SignInController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SIGN_IN_SERVLET).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginBindingModel loginModel = null;
        String signIn = req.getParameter(SIGN_IN);

        if (signIn != null) {
            String username = req.getParameter(USERNAME);
            String password = req.getParameter(PASSWORD);

            IUserService userServices = UserServiceFactory.create();
            loginModel = userServices.findUser(username, password);
        }
        if (loginModel != null) {
            HttpSession session = req.getSession();
            session.setAttribute(LOGIN_MODEL, loginModel);
            resp.sendRedirect(HOME_REDIRECT);
        } else {
            resp.sendRedirect(SIGN_IN_REDIRECT);
        }

    }
}
