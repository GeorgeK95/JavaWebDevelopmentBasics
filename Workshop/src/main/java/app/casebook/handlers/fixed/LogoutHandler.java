package main.java.app.casebook.handlers.fixed;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Post;
import main.java.app.casebook.enums.ContentType;
import main.java.app.javache.enums.Response;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.database.annotations.Repository;
import main.java.app.database.entities.User;
import main.java.app.database.repositories.user.IUserRepository;
import main.java.app.javache.annotations.HttpSession;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.test.contracts.IHttpSessionStorage;

import static main.java.app.casebook.utilities.ApplicationConstants.EMPTY;
import static main.java.app.casebook.utilities.ApplicationConstants.INDEX_HTML_PATH;
import static main.java.app.javache.utils.ServerConstants.JAVACHE_SESSION_ID;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class LogoutHandler extends BaseHandler {
    @Repository
    private IUserRepository<User> userRepository;
    @HttpSession
    private IHttpSessionStorage session;

    @Post(route = "/logout")
    public IHttpResponse buildResponseProcess(IHttpRequest request, IHttpResponse response) {
        if (request.getCookies().containsKey(JAVACHE_SESSION_ID)) {
            this.session.removeSession(JAVACHE_SESSION_ID);
            return super.handleEvent(request, response, Response.RESPONSE_MOVED_PERMANENTLY, ContentType.HTML,
                    INDEX_HTML_PATH, EMPTY);
        }

        return super.handleEvent(request, response, Response.BAD_REQUEST, ContentType.HTML,
                INDEX_HTML_PATH);
    }

}
