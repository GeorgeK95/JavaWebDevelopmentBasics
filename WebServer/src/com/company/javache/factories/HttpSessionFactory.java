package com.company.javache.factories;

import com.company.javache.http.HttpSession;
import com.company.javache.http.contracts.IHttpSession;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class HttpSessionFactory {
    public static IHttpSession create() {
        return new HttpSession();
    }

    private HttpSessionFactory() {
    }
}
