package com.company.javache.http.contracts;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpCookie {
    String getCookieName();

    String getCookieValue();

    void writeToDb(String cookieName, String cookieValue);

}
