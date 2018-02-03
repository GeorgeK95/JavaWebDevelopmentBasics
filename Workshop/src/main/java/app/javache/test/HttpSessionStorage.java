package main.java.app.javache.test;

import main.java.app.javache.test.contracts.IHttpCookie;
import main.java.app.javache.test.contracts.IHttpSessionStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpSessionStorage implements IHttpSessionStorage {

    private Map<String, Map<String, IHttpCookie>> sessions;//javache_Ses_id -> userId, lang, skinColor...

    public HttpSessionStorage() {
        this.sessions = new HashMap<>();
    }

    @Override
    public Map<String, Map<String, IHttpCookie>> getSessions() {
        return this.sessions;
    }

    @Override
    public void addSession(String sessionId, IHttpCookie cookie) {
        if (!this.sessions.containsKey(sessionId)) {
            this.sessions.put(sessionId,
                    new HashMap<String, IHttpCookie>() {{
                        put(cookie.getCookieName(), cookie);
                    }});
        } else {
            this.sessions.get(sessionId).put(cookie.getCookieName(), cookie);
        }
    }

    @Override
    public void removeSession(String sessionId) {
        this.sessions.remove(sessionId);
    }

    @Override
    public Map<String, IHttpCookie> sessionBySessionId(String sessionId) {
        return this.sessions.get(sessionId);
    }
}
