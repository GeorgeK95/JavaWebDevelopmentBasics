package javache.io;

import javache.io.contracts.StreamWriter;

import java.io.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class OutputStreamWriter implements StreamWriter {
    @Override
    public void writeOutputStream(byte[] outputStream, DataOutputStream responseStream) throws IOException {
        responseStream.write(outputStream);
    }
}
