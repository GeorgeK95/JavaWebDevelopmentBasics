package main.java.app;


import main.java.app.casebook.CasebookApplication;
import main.java.app.database.entities.User;
import main.java.app.database.repositories.user.IUserRepository;
import main.java.app.database.repositories.user.UserRepository;
import main.java.app.javache.Application;
import main.java.app.javache.http.factories.HttpSessionStorageFactory;
import main.java.app.javache.server.Server;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

import java.io.IOException;

import static main.java.app.javache.utils.ServerConstants.PORT;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class StartUp {

    public static void main(String[] args) throws IOException {
        IUserRepository<User> userRepository = new UserRepository();
        IHttpSessionStorage session = HttpSessionStorageFactory.create();

        Application application = new CasebookApplication();
        application.setRepository(userRepository);
        application.setSession(session);

        Server server = new Server(PORT, application);
        server.run();
    }
}
