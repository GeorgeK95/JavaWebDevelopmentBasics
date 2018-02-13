package com.company.javache.lib.handler;

import java.io.DataOutputStream;
import java.io.InputStream;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IRequestHandler {
    //    byte[] handleRequest(String requestContent,String rootPath) throws IOException;
    void handleRequest(InputStream requestStream, DataOutputStream responseStream);

    boolean hasIntercepted();
}
