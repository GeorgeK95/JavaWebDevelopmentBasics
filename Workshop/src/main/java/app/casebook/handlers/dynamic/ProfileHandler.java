package main.java.app.casebook.handlers.dynamic;


import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.casebook.handlers.contracts.DynamicHandler;
import main.java.app.javache.annotations.HttpSession;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

import static main.java.app.casebook.utilities.ApplicationConstants.PROFILE_HTML_PATH;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class ProfileHandler extends BaseHandler implements DynamicHandler {
    @HttpSession
    private IHttpSessionStorage session;

    @Get(route = "/users/profile")
    @Override
    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        return super.handleAuthenticated(response, PROFILE_HTML_PATH, this.session);
    }
}
