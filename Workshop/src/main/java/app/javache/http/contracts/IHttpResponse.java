package main.java.app.javache.http.contracts;

import main.java.app.javache.test.contracts.IHttpCookie;

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

    void addSession(String cookie, String value);

}
