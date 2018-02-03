package main.java.app.javache.enums;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public enum Response {
    RESPONSE_OK("ok", "HTTP/1.1 200 OK", 200),

    RESPONSE_UNAUTHORIZED("unauthorized", "HTTP/1.1 401 Unauthorized", 401),

    RESPONSE_MOVED_PERMANENTLY("moved permanently", "HTTP/1.1 303 See Other", 303),

    RESPONSE_NOT_FOUND("not_found", "HTTP/1.1 404 Not Found", 404),

    BAD_REQUEST("bad_request", "HTTP/1.1 400 Bad Request", 400);

    private final String key;
    private final String value;
    private final Integer code;

    Response(String key, String value, Integer code) {
        this.key = key;
        this.value = value;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
