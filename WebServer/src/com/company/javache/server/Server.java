package com.company.javache.server;

import com.company.javache.handlers.ConnectionHandler;
import com.company.javache.io.ConsoleOutputWriter;
import com.company.javache.lib.handler.IRequestHandler;
import com.company.javache.utils.HandlersLoader;
import com.company.javache.utils.config.IConfig;
import com.company.javache.utils.config.ServerConfig;
import com.teamdev.filewatch.FileEvent;
import com.teamdev.filewatch.FileEventsListener;
import com.teamdev.filewatch.FileWatcher;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.FutureTask;

import static com.company.javache.utils.ServerConstants.*;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class Server {

    private String rootPath;
    private int port;
    private ServerSocket serverSocket;
    private int timeoutsCounter;
    //    private Iterable<IRequestHandler> handlers;
    private Map<String, IRequestHandler> handlers;
    private IConfig config;
    private FileWatcher watcher;

    public Server(int port) {
        this.initializeServerData(port);
    }

    private void initializeServerData(int port) {
        this.port = port;
        this.rootPath = SERVER_ROOT_FOLDER;
        this.loadHandlers();
        this.setConfiguration();
        this.startMonitoringDirectory(SERVER_ROOT_FOLDER + STATIC_HANDLES_FOLDER);
    }

    private void setConfiguration() {
        IConfig config = new ServerConfig();
        config.readConfiguration();
        this.config = config;
    }

    private void loadHandlers() {
        this.handlers = HandlersLoader.loadRequestHandlers(SERVER_ROOT_FOLDER);

        /*TASK_8
        Runnable headersLoader = () -> {
            this.handlers = HandlersLoader.loadRequestHandlers(SERVER_ROOT_FOLDER);
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(headersLoader, 0, 10, TimeUnit.SECONDS);*/
    }

    private void startMonitoringDirectory(String targetDir) {
//        final Logger logger = Logger.getLogger(Server.class.getName());
        File tempFile = new File(targetDir);
        File watchingFolder = tempFile.getParentFile();

        this.watcher = FileWatcher.create(watchingFolder);

        //TASK_10
        watcher.addFileEventsListener(new FileEventsListener() {
            final String handlersPath = rootPath + STATIC_HANDLES_FOLDER;

            public void fileAdded(FileEvent.Added e) {
                //if new file is added, load it simultaneously will be last in the config.ini file
                this.addHandler(e.getFile());
            }

            public void fileDeleted(FileEvent.Deleted e) {
                //if file is deleted, this method deletes it from the server handlers collection
                handlers.remove(e.getFile().getName().replace(CLASS_TYPE, ""));
            }

            public void fileChanged(FileEvent.Changed e) {
                //in case of ease, repeat "fileAdded" logic
                this.addHandler(e.getFile());
            }

            public void fileRenamed(FileEvent.Renamed e) {
            }

            private void addHandler(File file) {
                Optional<Map.Entry<String, IRequestHandler>> nearlyLoadedHandlersOptional =
                        HandlersLoader.loadRequestHandlersFromFile(file, new HashMap<>())
                                .entrySet()
                                .stream().findAny();
                if (nearlyLoadedHandlersOptional.isPresent()) {
                    Map.Entry<String, IRequestHandler> nearlyLoadedHandlers = nearlyLoadedHandlersOptional.get();
                    handlers.put(nearlyLoadedHandlers.getKey(), nearlyLoadedHandlers.getValue());
                }
            }
        });

        /*//TASK_9
        watcher.addFileEventsListener(new FileEventsListener() {
            public void fileAdded(FileEvent.Added e) {
                handlers = HandlersLoader.loadRequestHandlers(SERVER_ROOT_FOLDER);
            }

            public void fileDeleted(FileEvent.Deleted e) {
                handlers = HandlersLoader.loadRequestHandlers(SERVER_ROOT_FOLDER);
            }

            public void fileChanged(FileEvent.Changed e) {
                handlers = HandlersLoader.loadRequestHandlers(SERVER_ROOT_FOLDER);
            }

            public void fileRenamed(FileEvent.Renamed e) {
            }
        });*/

        watcher.start();
    }

    public void run() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        this.serverSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
        ConsoleOutputWriter.writeOnConsole(String.format(WAITING_ON_PORT_D, this.port));

        while (true) {
            try (Socket clientSocket = this.serverSocket.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
                ConsoleOutputWriter.writeOnConsole(ESTABLISHED_CONNECTION);
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, this.handlers, this.rootPath, this.config);
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException ste) {
                ConsoleOutputWriter.writeOnConsole(TIMEOUT_DETECTION_MESSAGE);
                this.timeoutsCounter++;
            }
        }
    }
}
