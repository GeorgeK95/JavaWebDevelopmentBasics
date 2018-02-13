package com.company.javache.factories;

import com.company.javache.http.HttpSessionStorage;
import com.company.javache.http.contracts.IHttpSessionStorage;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class HttpSessionStorageFactory {
    public static IHttpSessionStorage create() {
        return new HttpSessionStorage();
    }

    private HttpSessionStorageFactory() {
    }
}
