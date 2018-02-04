package main.java.app.javache.http.factories;

import main.java.app.javache.http.HttpSession;
import main.java.app.javache.http.contracts.IHttpSession;

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
