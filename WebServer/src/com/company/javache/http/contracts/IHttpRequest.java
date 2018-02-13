package com.company.javache.http.contracts;

import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpRequest {
    Map<String, String> getHeaders();

    Map<String, String> getBodyParams();

    Map<String, String> getCookies();

    String getMethod();

    void setMethod(String method);

    String getUrl();

    void setUrl(String url);

    void addHeader(String header, String value);

    void addBodyParam(String bodyParam, String value);

    boolean isResource();
}
