package main.java.app.casebook.handlers.fixed;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.enums.ContentType;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.casebook.handlers.contracts.StaticHandler;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;

import static main.java.app.casebook.utilities.ApplicationConstants.INDEX_HTML_PATH;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class IndexHandler extends BaseHandler implements StaticHandler {

    @Get(route = "/")
    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        super.setTemplateOnResponseContent(INDEX_HTML_PATH, response);
        super.addContentTypeHeader(ContentType.HTML, response);
        return super.ok(response);
    }
}
