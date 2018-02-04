package main.java.app.javache.handlers;


import main.java.app.javache.handlers.contracts.IConnectionHandler;
import main.java.app.javache.io.InputStreamReader;
import main.java.app.javache.io.OutputStreamWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ConnectionHandler extends Thread implements IConnectionHandler {
    private RequestHandler requestHandler;
    private Socket clientSocket;

    private InputStream requestStream;
    private DataOutputStream responseStream;

    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.establishConnection(clientSocket);
        this.requestHandler = requestHandler;
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

    @Override
    public void run() {
        try {
            String requestContent = InputStreamReader.readClientInputStream(this.requestStream);
            byte[] handledRequest = this.requestHandler.handleRequest(requestContent);
            OutputStreamWriter.writeOutputStream(handledRequest, this.responseStream);
            this.closeSockets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSockets() throws IOException {
        this.requestStream.close();
        this.responseStream.close();
        this.clientSocket.close();
    }
}
