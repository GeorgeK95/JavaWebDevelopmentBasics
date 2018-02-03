package main.java.app.javache.handlers.contracts;

import java.io.IOException;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IRequestHandler {
    byte[] handleRequest(String requestContent) throws IOException;
}
