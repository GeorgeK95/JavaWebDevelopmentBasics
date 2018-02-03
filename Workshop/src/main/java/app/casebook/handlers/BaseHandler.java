package main.java.app.casebook.handlers;

import main.java.app.casebook.handlers.contracts.Handler;
import main.java.app.casebook.enums.ContentType;
import main.java.app.javache.enums.Response;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.io.InputStreamReader;
import main.java.app.javache.utils.ServerConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static main.java.app.casebook.utilities.ApplicationConstants.*;
import static main.java.app.javache.utils.ServerConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class BaseHandler implements Handler {
    private static Map<String, String> contentTypeLines;

    private IHttpRequest request;
    private IHttpResponse response;

    @Override
    public IHttpResponse handleEvent(IHttpRequest request, IHttpResponse response,
                                     Response responseType, ContentType type,
                                     String resourceAbsolutePath, String... additionalParams) {
        if (contentTypeLines == null) {
            this.setContentTypes();
        }
        this.request = request;
        this.response = response;
        if (!this.setHtmlContent(responseType, resourceAbsolutePath)) {
            return this.response;
        }
        this.setResponseStatus(responseType, additionalParams);
        this.addContentTypeHeader(type);
        return this.response;
    }

    private boolean setHtmlContent(Response responseType, String resourceAbsolutePath) {
        byte[] htmlContent = InputStreamReader.readResourceAndGetHtmlContent(resourceAbsolutePath);
        if (htmlContent == null || htmlContent.length == 0 || responseType == Response.RESPONSE_NOT_FOUND) {
            this.notFound();
            return false;
        }
        this.response.setContent(htmlContent);
        return true;
    }

    private void addContentTypeHeader(ContentType type) {
        switch (type) {
            case CSS:
                this.response.addHeader(ContentType.CSS.getKey(), ContentType.CSS.getValue());
                break;
            case HTML:
                this.response.addHeader(ContentType.HTML.getKey(), ContentType.HTML.getValue());
                break;
            case JPG:
                this.response.addHeader(ContentType.JPG.getKey(), ContentType.JPG.getValue());
                break;
            case PNG:
                this.response.addHeader(ContentType.PNG.getKey(), ContentType.PNG.getValue());
                break;
        }
    }

    private void setResponseStatus(Response responseType, String[] additionalParams) {
        switch (responseType) {
            case RESPONSE_OK:
                this.ok();
                break;
            case RESPONSE_MOVED_PERMANENTLY:
                this.Redirect(additionalParams[0]);
                break;
            default:
                this.badRequest();
                break;
        }
    }

    private void Redirect(String redirectTo) {
        this.response.setStatusCode(Response.RESPONSE_MOVED_PERMANENTLY.getCode());
        this.response.addHeader(Response.RESPONSE_MOVED_PERMANENTLY.getKey(), Response.RESPONSE_MOVED_PERMANENTLY.getValue());
        this.response.addHeader(LOCATION, LOCATION.concat("/" + redirectTo));
    }

    private void badRequest() {
        this.response.setStatusCode(Response.BAD_REQUEST.getCode());
        this.response.addHeader(Response.BAD_REQUEST.getKey(), Response.BAD_REQUEST.getValue());
    }

    private void notFound() {
        this.response.setStatusCode(Response.RESPONSE_NOT_FOUND.getCode());
        this.response.addHeader(Response.RESPONSE_NOT_FOUND.getKey(), Response.RESPONSE_NOT_FOUND.getValue());
        this.seedCommonDataToResponse();
    }

    private void Unauthorized() {
        this.response.setStatusCode(Response.RESPONSE_UNAUTHORIZED.getCode());
        this.response.addHeader(Response.RESPONSE_UNAUTHORIZED.getKey(), Response.RESPONSE_UNAUTHORIZED.getValue());
    }

    private void ok() {
        this.response.setStatusCode(Response.RESPONSE_OK.getCode());
        this.response.addHeader(Response.RESPONSE_OK.getKey(), Response.RESPONSE_OK.getValue());
        this.seedCommonDataToResponse();
    }

    private void seedCommonDataToResponse() {
        this.response.addHeader(ServerConstants.SERVER_STRING, SERVER_HEADER_NAME_AND_VERSION);
        this.response.addHeader(DATE, contentTypeLines.get(DATE));
        this.response.addHeader(INLINE_STRING, contentTypeLines.get(INLINE_STRING));
    }

    private void setContentTypes() {
        contentTypeLines = new HashMap<>();
        contentTypeLines.put(LOCATION, LOCATION.concat("/" + LOGIN));
        contentTypeLines.put(DATE, DATE.concat(new Date().toString()));
        contentTypeLines.put(INLINE_STRING, CONTENT_DISPOSITION_STRING.concat("inline"));
    }
}
