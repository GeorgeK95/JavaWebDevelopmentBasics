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
import main.java.app.javache.http.HttpCookie;
import main.java.app.javache.http.contracts.IHttpCookie;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

import java.util.UUID;

import static main.java.app.casebook.utilities.ApplicationConstants.LOGIN_HTML_PATH;
import static main.java.app.javache.utils.ServerConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class LoginHandler extends BaseHandler implements StaticHandler {
    @Repository
    private IUserRepository<User> userRepository;
    @HttpSession
    private IHttpSessionStorage sessionStorage;

    @Get(route = "/html/login.html")
    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        return super.handleUnauthenticated(response, LOGIN_HTML_PATH, this.sessionStorage);
    }

    @Post(route = "/html/login.html")
    public IHttpResponse buildResponseProcess(IHttpRequest request, IHttpResponse response) {
        String targetEmail = request.getBodyParams().get(UserProperty.EMAIL.name().toLowerCase());
        String targetPassword = request.getBodyParams().get(UserProperty.PASSWORD.name().toLowerCase());
        User foundUser = this.userRepository.findByEmail(targetEmail);
        String template;

        if (foundUser != null) {
            if (foundUser.getPassword().equals(targetPassword)) {
                String sessionId = UUID.randomUUID().toString();
                IHttpCookie userCookie = new HttpCookie(USER_ID, foundUser.getId());
                this.sessionStorage.getSessions().get(JAVACHE_SESSION_ID).addCookie(userCookie);
                response.addSession(JAVACHE_SESSION_ID, sessionId);
                return super.redirect(response, USERS_PROFILE_PAGE);
            }
        }

        super.setTemplateOnResponseContent(LOGIN_HTML_PATH, response);
        return super.badRequest(response);
    }
}
