package main.java.app.casebook.enums;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public enum ContentType {
    HTML("html", "Content-Type: text/html"),

    CSS("css", "Content-Type: text/css"),

    PNG("png", "Content-Type: image/png"),

    JPEG("jpeg", "Content-Type: image/jpeg"),

    JPG("jpg", "Content-Type: image/jpg");

    private final String key;
    private final String value;

    ContentType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
