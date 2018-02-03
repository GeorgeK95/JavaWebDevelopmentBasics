package main.java.app.javache.http;

import main.java.app.javache.http.contracts.IHttpResponse;

import java.util.LinkedHashMap;
import java.util.Map;

import static main.java.app.javache.utils.ServerConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpResponse implements IHttpResponse {
    private Map<String, String> headers;
    private int statusCode;
    private byte[] content;

    public HttpResponse() {
        this.headers = new LinkedHashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        StringBuilder headersBuilder = new StringBuilder();
        for (String currentHeader : this.headers.values()) {
            headersBuilder.append(currentHeader).append(System.lineSeparator());
        }
        headersBuilder.append(System.lineSeparator());
        return this.constructFinalByteArray(headersBuilder);
    }

    @Override
    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addSession(String cookie, String value) {
        String pathString = "; path=/";
        String fullCookieString = cookie + EQUAL_SPLITTER + value + pathString;
        if (!this.headers.containsKey(SET_COOKIE)) {
            this.headers.put(SET_COOKIE, SET_COOKIE.concat(fullCookieString));
        } else {
            this.headers.put(SET_COOKIE, this.headers.get(SET_COOKIE).concat(SEMICOLON_SPLITTER).concat(fullCookieString));
        }
    }

    private byte[] constructFinalByteArray(StringBuilder headersBuilder) {
        byte[] headersBytes = headersBuilder.toString().getBytes();
        byte[] finalByteArray = new byte[headersBytes.length + this.content.length];
        int i = 0;
        for (byte currentHeaderByte : headersBytes) {
            finalByteArray[i++] = currentHeaderByte;
        }
        for (byte currentImageByte : this.content) {
            finalByteArray[i++] = currentImageByte;
        }
        return finalByteArray;
    }

}
