package com.company.javache.io;

import java.io.*;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class InputStreamReader {
    private InputStreamReader() {
    }

    public static String readClientInputStream(InputStream requestStream) throws IOException {
        BufferedReader buffer = new BufferedReader(new java.io.InputStreamReader(requestStream));
        StringBuilder builder = new StringBuilder();
        while (buffer.ready()) {
            builder.append((char) buffer.read());
        }
        return builder.toString();
    }

    public static byte[] readResourceAndGetHtmlContent(String absPath) {
        File f = new File(absPath);
        try {
            if (f.exists()) {
                return InputStreamReader.readClientInputStream(new FileInputStream(absPath)).getBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
