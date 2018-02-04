package main.java.app.casebook.handlers.fixed;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.casebook.handlers.contracts.StaticHandler;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class PageNotFoundHandler extends BaseHandler implements StaticHandler {

    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        return super.notFound(response);
    }
}
