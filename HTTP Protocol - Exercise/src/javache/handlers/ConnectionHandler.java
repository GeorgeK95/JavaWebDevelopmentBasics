package javache.handlers;

import javache.io.InputStreamReader;
import javache.io.OutputStreamWriter;
import javache.io.contracts.StreamWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ConnectionHandler extends Thread {
    private RequestHandler requestHandler;
    private Socket clientSocket;

    private StreamWriter writer;

    private InputStream requestStream;
    private DataOutputStream responseStream;

    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.establishConnection(clientSocket);
        this.requestHandler = requestHandler;
    }

    private void establishConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.requestStream = this.clientSocket.getInputStream();
            this.responseStream = new DataOutputStream(this.clientSocket.getOutputStream());
            this.writer = new OutputStreamWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            //read request
            String requestContent = InputStreamReader.readClientInputStream(this.requestStream);
            //pass it to the RH then RH should return byte[]
            byte[] handledRequest = this.requestHandler.handleRequest(requestContent);
            //CH send response
            this.writer.writeOutputStream(handledRequest, this.responseStream);
            //close opened sockets
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
