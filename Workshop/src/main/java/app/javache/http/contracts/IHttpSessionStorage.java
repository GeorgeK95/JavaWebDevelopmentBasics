package main.java.app.javache.http.contracts;

import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpSessionStorage {
    Map<String, IHttpSession> getSessions();

    boolean addSession(String sessionId, IHttpSession cookie);

    void removeSession(String sessionId);

    IHttpSession sessionBySessionId(String sessionId);
}
