package com.company.javache.handlers;


import com.company.javache.handlers.contracts.IConnectionHandler;
import com.company.javache.io.InputStreamReader;
import com.company.javache.lib.handler.IRequestHandler;
import com.company.javache.utils.config.IConfig;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ConnectionHandler extends Thread implements IConnectionHandler {
    private Map<String, IRequestHandler> handlersByHandlerClassName;
    private Socket clientSocket;

    private InputStream requestStream;
    private DataOutputStream responseStream;
    private String rootPath;
    private IConfig config;
    private ByteArrayOutputStream baos;
    private String cachedInputStream;

    public ConnectionHandler(Socket clientSocket, Map<String, IRequestHandler> requestHandlers, String rootPath, IConfig config) {
        this.initData(clientSocket, requestHandlers, rootPath, config);
    }

    private void initData(Socket clientSocket, Map<String, IRequestHandler> requestHandlers, String rootPath, IConfig config) {
        this.establishConnection(clientSocket);
        this.handlersByHandlerClassName = requestHandlers;
//        requestHandlers.values().forEach(h -> this.handlersByHandlerClassName.put(h.getClass().getSimpleName(), h));
        this.rootPath = rootPath;
        this.config = config;
    }

    @Override
    public void establishConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.requestStream = this.clientSocket.getInputStream();
            this.responseStream = new DataOutputStream(this.clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ByteArrayInputStream getRequestStream() throws IOException {
        if (this.cachedInputStream == null)
            this.cachedInputStream = InputStreamReader.readClientInputStream(this.requestStream);

        return new ByteArrayInputStream(this.cachedInputStream.getBytes());
    }

    @Override
    public void run() {
        try {
            this.handleRequests(this.config.getConfigurationContent());
            this.closeSockets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean handleRequests(List<String> handlers) throws IOException {
        for (String currentHandler : handlers) {
            IRequestHandler requestHandler = this.handlersByHandlerClassName.get(currentHandler);
            if (requestHandler != null) {
                requestHandler.handleRequest(this.getRequestStream(), this.responseStream);
                if (requestHandler.hasIntercepted()) return true;
            }
        }

        return false;
    }

    private void closeSockets() throws IOException {
        this.requestStream.close();
        this.responseStream.close();
        this.clientSocket.close();
    }
}
