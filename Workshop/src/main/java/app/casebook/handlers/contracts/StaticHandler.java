package main.java.app.casebook.handlers.contracts;

import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface StaticHandler {
    IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response);
}
