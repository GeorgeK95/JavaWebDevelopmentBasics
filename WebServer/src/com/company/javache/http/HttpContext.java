package com.company.javache.http;

import com.company.javache.http.contracts.IHttpContext;
import com.company.javache.http.contracts.IHttpRequest;
import com.company.javache.http.contracts.IHttpResponse;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpContext implements IHttpContext {
    private IHttpRequest request;
    private IHttpResponse response;

    public HttpContext(IHttpRequest request, IHttpResponse response) {
        this.setRequest(request);
        this.setResponse(response);
    }

    private void setRequest(IHttpRequest request) {
        this.request = request;
    }

    private void setResponse(IHttpResponse response) {
        this.response = response;
    }

    @Override
    public IHttpRequest getRequest() {
        return this.request;
    }

    @Override
    public IHttpResponse getResponse() {
        return this.response;
    }
}
