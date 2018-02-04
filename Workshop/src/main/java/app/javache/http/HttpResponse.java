package main.java.app.javache.http;

import main.java.app.javache.enums.HeaderPriority;
import main.java.app.javache.http.contracts.IHttpResponse;

import java.util.*;

import static main.java.app.javache.utils.ServerConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HttpResponse implements IHttpResponse {
    private Map<Integer, Map<String, String>> headers;
    private int statusCode;
    private byte[] content;

    public HttpResponse() {
        this.headers = new TreeMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.getHeadersNastedValues();
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
        Collection<String> toBeRemoved = this.getHeadersNastedValues().values();
        for (String currentHeader : toBeRemoved) {
            headersBuilder.append(currentHeader).append(System.lineSeparator());
        }
        headersBuilder.append(System.lineSeparator());
        return this.constructFinalByteArray(headersBuilder);
    }

    private Map<String, String> getHeadersNastedValues() {
        Map<String, String> temp = new LinkedHashMap<>();
        for (Map<String, String> currentHeaderMap : this.headers.values()) {
            temp.putAll(currentHeaderMap);
        }
        return temp;
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
    public void addHeader(HeaderPriority priority, String header, String value) {
        if (!this.headers.containsKey(priority.getPriority())) {
            this.headers.put(priority.getPriority(), new LinkedHashMap<>());
        }
        this.headers.get(priority.getPriority()).put(header, value);
    }

    @Override
    public void addSession(String cookie, String value) {
        this.addSession(HeaderPriority.LOW, cookie, value);
    }

    private void addSession(HeaderPriority priority, String cookie, String value) {
        String pathString = "; path=/";
        String fullCookieString = cookie + EQUAL_SPLITTER + value + pathString;
        String concat;
        if (!this.headers.containsKey(priority.getPriority())) {
            this.headers.put(priority.getPriority(), new LinkedHashMap<>());
        }
        if (!this.headers.get(priority.getPriority()).containsKey(SET_COOKIE)) {
            concat = SET_COOKIE.concat(fullCookieString);
        } else {
            concat = this.headers.get(priority.getPriority()).get(SET_COOKIE).concat(SEMICOLON_SPLITTER)
                    .concat(fullCookieString);
        }
        this.headers.get(priority.getPriority()).put(SET_COOKIE, concat);
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
