package main.java.app.javache.io.contracts;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface StreamReader {
    String readClientInputStream(InputStream requestStream) throws IOException;

    byte[] readAndGetTargetResource(String targetResource);
}
