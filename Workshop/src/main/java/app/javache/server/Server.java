package main.java.app.javache.server;


import main.java.app.javache.Application;
import main.java.app.javache.handlers.ConnectionHandler;
import main.java.app.javache.handlers.RequestHandler;
import main.java.app.javache.io.contracts.ConsoleWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

import static main.java.app.javache.utils.ServerConstants.*;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class Server {

    private int port;
    private ServerSocket serverSocket;
    private int timeoutsCounter;
    private ConsoleWriter writer;
    private Application application;

    public Server(int port, ConsoleWriter writer, Application application) {
        this.port = port;
        this.writer = writer;
        this.application = application;
    }

    public void run() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        this.serverSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
        this.writer.writeOnConsole(String.format(WAITING_ON_PORT_D, this.port));

        while (true) {
            try (Socket clientSocket = this.serverSocket.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
                this.writer.writeOnConsole(ESTABLISHED_CONNECTION);
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler(application));
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException ste) {
                this.writer.writeOnConsole(TIMEOUT_DETECTION_MESSAGE);
                this.timeoutsCounter++;
            }
        }
    }
}
