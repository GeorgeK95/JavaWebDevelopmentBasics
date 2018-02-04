package main.java.app.casebook.handlers;

import main.java.app.casebook.enums.ContentType;
import main.java.app.casebook.handlers.contracts.Handler;
import main.java.app.casebook.providers.IProvider;
import main.java.app.casebook.providers.factories.ProviderFactory;
import main.java.app.javache.enums.HeaderPriority;
import main.java.app.javache.enums.Response;
import main.java.app.javache.http.contracts.IHttpCookie;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.http.contracts.IHttpSessionStorage;
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
    protected IProvider templateProvider;
    protected IProvider viewProvider;

    private static Map<String, String> contentTypeLines;
    private IHttpRequest request;
    private IHttpResponse response;

    protected void addContentTypeHeader(ContentType type, IHttpResponse response) {
        switch (type) {
            case HTML:
                response.addHeader(HeaderPriority.MEDIUM, ContentType.HTML.getKey(), ContentType.HTML.getValue());
                break;
            case PNG:
                response.addHeader(HeaderPriority.MEDIUM, ContentType.PNG.getKey(), ContentType.PNG.getValue());
                break;
        }
    }

    protected IHttpResponse redirect(IHttpResponse response, String redirectTo) {
        response.setStatusCode(Response.RESPONSE_MOVED_PERMANENTLY.getCode());
        response.addHeader(HeaderPriority.HIGH, Response.RESPONSE_MOVED_PERMANENTLY.getKey(), Response.RESPONSE_MOVED_PERMANENTLY.getValue());
        this.addContentTypeHeader(ContentType.HTML, response);
        response.addHeader(HeaderPriority.LOW, LOCATION, LOCATION.concat("/" + redirectTo));
        this.setTemplateOnResponseContent(LOGIN_HTML_PATH, response);
        return response;
    }

    protected IHttpResponse badRequest(IHttpResponse response) {
        response.setStatusCode(Response.BAD_REQUEST.getCode());
        response.addHeader(HeaderPriority.HIGH, Response.BAD_REQUEST.getKey(), Response.BAD_REQUEST.getValue());
        this.addContentTypeHeader(ContentType.HTML, response);
        return response;
    }

    protected IHttpResponse notFound(IHttpResponse response) {
        response.setStatusCode(Response.RESPONSE_NOT_FOUND.getCode());
        response.addHeader(HeaderPriority.HIGH, Response.RESPONSE_NOT_FOUND.getKey(), Response.RESPONSE_NOT_FOUND.getValue());
        addContentTypeHeader(ContentType.HTML, response);
//        response.setContent(this.setTemplateOnResponseContent(ERROR_404_HTML_PATH, response).getBytes());
        response.setContent(InputStreamReader.readResourceAndGetHtmlContent(ERROR_404_HTML_PATH));
        return response;
    }

    protected IHttpResponse unauthorized(IHttpResponse response, String resource) {
        response.setStatusCode(Response.RESPONSE_UNAUTHORIZED.getCode());
        response.addHeader(HeaderPriority.HIGH, Response.RESPONSE_UNAUTHORIZED.getKey(), Response.RESPONSE_UNAUTHORIZED.getValue());
        this.addContentTypeHeader(ContentType.HTML, response);
        this.setTemplateOnResponseContent(resource, response);
        return response;
    }

    protected IHttpResponse ok(IHttpResponse response) {
        response.setStatusCode(Response.RESPONSE_OK.getCode());
        response.addHeader(HeaderPriority.HIGH, Response.RESPONSE_OK.getKey(), Response.RESPONSE_OK.getValue());
        this.seedCommonDataToResponse(response);
        return response;
    }

    private void seedCommonDataToResponse(IHttpResponse response) {
        response.addHeader(HeaderPriority.LOW, ServerConstants.SERVER_STRING, SERVER_HEADER_NAME_AND_VERSION);
        if (contentTypeLines == null) {
            this.setContentTypes();
        }
        response.addHeader(HeaderPriority.LOW, DATE, contentTypeLines.get(DATE));
        response.addHeader(HeaderPriority.MEDIUM, INLINE_STRING, contentTypeLines.get(INLINE_STRING));
    }

    private void setContentTypes() {
        contentTypeLines = new HashMap<>();
        contentTypeLines.put(LOCATION, LOCATION.concat("/" + LOGIN));
        contentTypeLines.put(DATE, DATE.concat(new Date().toString()));
        contentTypeLines.put(INLINE_STRING, CONTENT_DISPOSITION_STRING.concat("inline"));
    }

    protected IHttpResponse setTemplateOnResponseContent(String resourcePath, IHttpResponse response) {
        if (this.templateProvider == null) {
            this.templateProvider = ProviderFactory.createTemplateProvider();
        }
        String provided = this.templateProvider.provide(resourcePath);
        response.setContent(provided.getBytes());
        return response;
    }

    protected IHttpResponse setViewOnResponseContent(String resourcePath, IHttpResponse response) {
        if (this.viewProvider == null) {
            this.viewProvider = ProviderFactory.createViewProvider();
        }
        String providedTemplate = this.templateProvider.provide(resourcePath);
        String provided = this.viewProvider.provide(providedTemplate);
        response.setContent(provided.getBytes());
        return response;
    }

    protected IHttpResponse handleAuthenticated(IHttpResponse response, String templatePath, IHttpSessionStorage session) {
        IHttpCookie iHttpCookie = session.getSessions().get(JAVACHE_SESSION_ID).getCookies().get(USER_ID);
        if (iHttpCookie != null) {
            this.setTemplateOnResponseContent(templatePath, response).getBytes();
            this.addContentTypeHeader(ContentType.HTML, response);
            return this.ok(response);
        }
        return this.redirect(response, LOGIN);
    }

    protected IHttpResponse handleUnauthenticated(IHttpResponse response, String templatePath, IHttpSessionStorage session) {
        IHttpCookie iHttpCookie = session.getSessions().get(JAVACHE_SESSION_ID).getCookies().get(USER_ID);
        if (iHttpCookie == null) {
            this.setTemplateOnResponseContent(templatePath, response);
            this.addContentTypeHeader(ContentType.HTML, response);
            return this.ok(response);
        }
        return this.redirect(response, PROFILE);
    }
}
