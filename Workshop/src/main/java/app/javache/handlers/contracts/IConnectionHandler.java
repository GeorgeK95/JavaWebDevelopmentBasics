package main.java.app.javache.handlers.contracts;

import java.net.Socket;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IConnectionHandler {
    void establishConnection(Socket clientSocket);
}
