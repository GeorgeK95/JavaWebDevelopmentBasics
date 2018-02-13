package com.company.javache.http.contracts;

import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpSession {
    Map<String, IHttpCookie> getCookies();

    void addCookie(IHttpCookie cookie);

    void removeCookie(String userId);
}
