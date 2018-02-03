package main.java.app.casebook.handlers.dynamic;


import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.enums.ContentType;
import main.java.app.javache.enums.Response;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.javache.annotations.HttpSession;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.test.contracts.IHttpCookie;
import main.java.app.javache.test.contracts.IHttpSessionStorage;

import java.util.Map;

import static main.java.app.casebook.utilities.ApplicationConstants.*;
import static main.java.app.javache.utils.ServerConstants.JAVACHE_SESSION_ID;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class HomeHandler extends BaseHandler {
    @HttpSession
    private IHttpSessionStorage session;

    @Get(route = "/home")
    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        if (this.session.getSessions().containsKey(JAVACHE_SESSION_ID)) {
            Map<String, IHttpCookie> iHttpCookies = this.session.sessionBySessionId(JAVACHE_SESSION_ID);
            if (iHttpCookies != null) {
                return super.handleEvent(request, response, Response.RESPONSE_OK, ContentType.HTML, HOME_HTML_PATH);
            }
        }

        return super.handleEvent(request, response, Response.RESPONSE_MOVED_PERMANENTLY, ContentType.HTML, PROFILE_HTML_PATH, LOGIN);
    }


}
