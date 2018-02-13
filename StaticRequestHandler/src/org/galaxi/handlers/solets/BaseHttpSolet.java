package org.galaxi.handlers.solets;

import org.galaxi.handlers.solets.contracts.IHttpSolet;
import org.galaxi.handlers.solets.contracts.IHttpSoletRequest;
import org.galaxi.handlers.solets.contracts.IHttpSoletResponse;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public abstract class BaseHttpSolet implements IHttpSolet {

    private static final String NOT_IMPLEMENTED_MESSAGE = "Method %s is not implemented.";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String OK = "ok";
    private static final String HTML = "html";
    private static final String HTTP_1_1_501_NOT_IMPLEMENTED = "HTTP/1.1 501 Not Implemented";
    private static final String CONTENT_TYPE_TEXT_HTML = "Content-Type: text/html";

    @Override
    public void doGet(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse) {
        this.notImplemented(soletResponse, GET);
    }

    @Override
    public void doPost(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse) {
        this.notImplemented(soletResponse, POST);
    }

    @Override
    public void doPut(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse) {
        this.notImplemented(soletResponse, PUT);
    }

    @Override
    public void doDelete(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse) {
        this.notImplemented(soletResponse, DELETE);
    }

    private void notImplemented(IHttpSoletResponse res, String method) {
        res.addHeader(OK, HTTP_1_1_501_NOT_IMPLEMENTED);
        res.addHeader(HTML, CONTENT_TYPE_TEXT_HTML);
        res.setContent(String.format(NOT_IMPLEMENTED_MESSAGE, method).getBytes());
    }
}
