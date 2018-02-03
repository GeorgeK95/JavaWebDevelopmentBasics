package javache.server;

import javache.handlers.ConnectionHandler;
import javache.handlers.RequestHandler;
import javache.http.HttpSession;
import javache.http.contracts.IHttpSession;
import javache.io.contracts.ConsoleWriter;
import javache.model.User;
import javache.repositories.IUserRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

import static javache.utils.WebConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class Server {

    private int port;
    private ServerSocket serverSocket;
    private int timeoutsCounter;
    private ConsoleWriter writer;
    private IUserRepository<User> userRepository;

    public Server(int port, ConsoleWriter writer, IUserRepository<User> userRepository) {
        this.port = port;
        this.writer = writer;
        this.userRepository = userRepository;
    }

    public void run() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        this.serverSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
        this.writer.writeOnConsole(String.format(WAITING_ON_PORT_D, this.port));
        IHttpSession session = new HttpSession();

        while (true) {
            try (Socket clientSocket = this.serverSocket.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
                this.writer.writeOnConsole(ESTABLISHED_CONNECTION);
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler(this.userRepository,session));
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException ste) {
                this.writer.writeOnConsole(TIMEOUT_DETECTION_MESSAGE);
                this.timeoutsCounter++;
            }
        }
    }
}
