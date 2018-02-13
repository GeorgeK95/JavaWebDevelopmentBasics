package com.company.javache.http.contracts;

import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpResponse {
    Map<String, String> getHeaders();

    int getStatusCode();

    byte[] getContent();

    byte[] getBytes();

    void setStatusCode(int code);

    void setContent(byte[] content);

    void addHeader(String header, String value);

    void addCookie(String cookie, String value);
}
