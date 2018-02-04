package main.java.app.javache.http.factories;

import main.java.app.javache.http.HttpSessionStorage;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

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
