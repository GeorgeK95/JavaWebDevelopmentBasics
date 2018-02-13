package com.company.javache.http;

import com.company.javache.http.contracts.IHttpCookie;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpCookie implements IHttpCookie {

    //userId -> u123132userId2132131
    private String cookieName;
    private String cookieValue;

    public HttpCookie(String cookieName, String cookieValue) {
        this.cookieName = cookieName;
        this.cookieValue = cookieValue;
    }

    @Override
    public String getCookieName() {
        return this.cookieName;
    }

    @Override
    public String getCookieValue() {
        return this.cookieValue;
    }

    @Override
    public void writeToDb(String cookieName, String cookieValue) {
        throw new NotImplementedException();
    }

}
