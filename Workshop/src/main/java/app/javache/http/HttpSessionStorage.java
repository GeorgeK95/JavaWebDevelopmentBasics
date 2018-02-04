package main.java.app.javache.http;

import main.java.app.javache.http.contracts.IHttpSession;
import main.java.app.javache.http.contracts.IHttpSessionStorage;
import main.java.app.javache.http.factories.HttpSessionFactory;
import main.java.app.javache.utils.ServerConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpSessionStorage implements IHttpSessionStorage {

    private Map<String, IHttpSession> sessions;

    public HttpSessionStorage() {
        this.initializeServerSession();
    }

    private void initializeServerSession() {
        this.sessions = new HashMap<String, IHttpSession>() {{
            put(ServerConstants.JAVACHE_SESSION_ID, HttpSessionFactory.create());
        }};
    }

    public Map<String, IHttpSession> getSessions() {
        return this.sessions;
    }

    public boolean addSession(String sessionId, IHttpSession session) {
        if (!this.sessions.containsKey(sessionId)) {
            this.sessions.put(sessionId,
                    session);
            return true;
        }

        return false;
    }

    public void removeSession(String sessionId) {
        this.sessions.remove(sessionId);
    }

    public IHttpSession sessionBySessionId(String sessionId) {
        return this.sessions.get(sessionId);
    }
}
