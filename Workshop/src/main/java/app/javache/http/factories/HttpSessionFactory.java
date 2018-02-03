package main.java.app.javache.http.factories;

import main.java.app.javache.test.HttpSessionStorage;
import main.java.app.javache.test.contracts.IHttpSessionStorage;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpSessionFactory {
    public static IHttpSessionStorage create() {
        return new HttpSessionStorage();
    }
}
