package main.java.app.casebook.handlers.fixed;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.annotations.Post;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.casebook.handlers.contracts.StaticHandler;
import main.java.app.database.annotations.Repository;
import main.java.app.database.entities.User;
import main.java.app.database.enums.UserProperty;
import main.java.app.database.repositories.user.IUserRepository;
import main.java.app.javache.annotations.HttpSession;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

import java.util.Map;

import static main.java.app.casebook.utilities.ApplicationConstants.LOGIN;
import static main.java.app.casebook.utilities.ApplicationConstants.REGISTER_HTML_PATH;
import static main.java.app.database.utils.DatabaseConstants.DISMISS_METHOD_NAME;
import static main.java.app.database.utils.DatabaseConstants.PERSIST_METHOD_NAME;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class RegisterHandler extends BaseHandler implements StaticHandler {
    @Repository
    private IUserRepository<User> userRepository;
    @HttpSession
    private IHttpSessionStorage sessionStorage;

    @Get(route = "/html/register.html")
    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        return super.handleUnauthenticated(response, REGISTER_HTML_PATH, this.sessionStorage);
    }

    @Post(route = "/html/register.html")
    public IHttpResponse buildResponseProcess(IHttpRequest request, IHttpResponse response) {
        Map<String, String> userData = request.getBodyParams();
        String username = userData.get(UserProperty.EMAIL.name().toLowerCase());
        String password = userData.get(UserProperty.PASSWORD.name().toLowerCase());
        String confirm = userData.get(UserProperty.CONFIRM.name().toLowerCase());
        return this.tryToRegister(username, password, confirm, response);
    }

    private IHttpResponse tryToRegister(String email, String password, String confirm, IHttpResponse response) {
        super.setTemplateOnResponseContent(REGISTER_HTML_PATH, response);
        if (!this.userRepository.exists(email) && password.equals(confirm)) {
            this.userRepository.invokeMethod(PERSIST_METHOD_NAME, email, password);
            this.userRepository.invokeMethod(DISMISS_METHOD_NAME, email, password);
            return super.redirect(response, LOGIN);
        }
        return super.badRequest(response);
    }

}
