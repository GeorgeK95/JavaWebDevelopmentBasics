package main.java.app.javache.http.contracts;

import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpSession {
    Map<String, Map<String, Object>> getSessions();

    Map<String, Object> getSingleSessionData(String sessionId);

    void setSession(String sessionId, Map<String, Object> dataMap);

    void removeSession(String sessionId);
}
