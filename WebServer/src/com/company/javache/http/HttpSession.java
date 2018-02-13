package com.company.javache.http;

import com.company.javache.http.contracts.IHttpCookie;
import com.company.javache.http.contracts.IHttpSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpSession implements IHttpSession {
    private Map<String, IHttpCookie> cookies;

    public HttpSession() {
        this.cookies = new HashMap<>();
    }

    @Override
    public Map<String, IHttpCookie> getCookies() {
        return Collections.unmodifiableMap(this.cookies);
    }

    @Override
    public void addCookie(IHttpCookie cookie) {
        this.cookies.put(cookie.getCookieName(), cookie);
    }

    @Override
    public void removeCookie(String userId) {
        this.cookies.remove(userId);
    }
}
