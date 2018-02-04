package main.java.app.casebook.handlers.fixed;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.annotations.Post;
import main.java.app.casebook.enums.ContentType;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.casebook.handlers.contracts.StaticHandler;
import main.java.app.database.annotations.Repository;
import main.java.app.database.entities.User;
import main.java.app.database.repositories.user.IUserRepository;
import main.java.app.javache.annotations.HttpSession;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

import static main.java.app.casebook.utilities.ApplicationConstants.INDEX;
import static main.java.app.javache.utils.ServerConstants.JAVACHE_SESSION_ID;
import static main.java.app.javache.utils.ServerConstants.USER_ID;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class LogoutHandler extends BaseHandler  implements StaticHandler {
    @Repository
    private IUserRepository<User> userRepository;
    @HttpSession
    private IHttpSessionStorage session;

    @Post(route = "/logout")
    public IHttpResponse buildResponseProcess(IHttpRequest request, IHttpResponse response) {
        super.addContentTypeHeader(ContentType.HTML, response);
        if (request.getCookies().containsKey(JAVACHE_SESSION_ID)) {
            this.session.getSessions().get(JAVACHE_SESSION_ID).removeCookie(USER_ID);
            return super.redirect(response, INDEX);
        }

        return super.badRequest(response);
    }

    @Get(route = "/logout")
    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        return null;
    }
}
