package main.java.app.casebook.handlers.contracts;

import main.java.app.casebook.enums.ContentType;
import main.java.app.javache.enums.Response;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface Handler {
    IHttpResponse handleEvent(IHttpRequest request, IHttpResponse response,
                              Response responseType, ContentType type,
                              String resourceAbsolutePath, String... additionalParams);
}
