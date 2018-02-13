package com.company.javache.io;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class OutputStreamWriter {
    private OutputStreamWriter() {
    }

    public static void writeOutputStream(byte[] outputStream, DataOutputStream responseStream) throws IOException {
        responseStream.write(outputStream);
    }
}
