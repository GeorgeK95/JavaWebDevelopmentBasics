package javache.http;

import javache.http.contracts.IHttpSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpSession implements IHttpSession {

    private Map<String, Map<String, Object>> sessions;

    public HttpSession() {
        this.sessions = new HashMap<>();
    }

    @Override
    public Map<String, Map<String, Object>> getSessions() {
        return this.sessions;
    }

    @Override
    public Map<String, Object> getSingleSessionData(String sessionId) {
        return this.getSessions().get(sessionId);
    }

    @Override
    public void setSession(String sessionId, Map<String, Object> dataMap) {
        if (!this.sessions.containsKey(sessionId)) {
            this.sessions.put(sessionId, dataMap);
        } else {
            for (Map.Entry<String, Map<String, Object>> kvp : this.sessions.entrySet()) {
                this.sessions.get(sessionId).put(kvp.getKey(), kvp.getValue());
            }
        }
    }

    @Override
    public void removeSession(String sessionId) {
        this.sessions.remove(sessionId);
    }
}
