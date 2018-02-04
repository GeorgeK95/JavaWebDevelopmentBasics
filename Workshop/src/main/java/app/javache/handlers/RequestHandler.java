package main.java.app.javache.handlers;

import main.java.app.javache.Application;
import main.java.app.javache.handlers.contracts.IRequestHandler;
import main.java.app.javache.http.HttpContext;
import main.java.app.javache.http.HttpRequest;
import main.java.app.javache.http.HttpResponse;
import main.java.app.javache.http.contracts.IHttpContext;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;

import java.io.IOException;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class RequestHandler implements IRequestHandler {
    private IHttpContext httpContext;
    private Application application;

    public RequestHandler(Application application) {
        this.application = application;
    }

    @Override
    public byte[] handleRequest(String requestContent) throws IOException {
        IHttpRequest request = new HttpRequest(requestContent);
        IHttpResponse response = new HttpResponse();

        this.httpContext = new HttpContext(request, response);
        return this.application.handleRequest(this.httpContext);
    }
}