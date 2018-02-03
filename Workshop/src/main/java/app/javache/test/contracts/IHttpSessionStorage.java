package main.java.app.javache.test.contracts;

import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpSessionStorage {
    Map<String,  Map<String, IHttpCookie>> getSessions();

    void addSession(String sessionId, IHttpCookie cookie);

    void removeSession(String sessionId);

    Map<String, IHttpCookie> sessionBySessionId(String sessionId);
}
