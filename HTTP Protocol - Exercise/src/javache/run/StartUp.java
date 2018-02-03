package javache.run;

import javache.io.ConsoleOutputWriter;
import javache.io.contracts.ConsoleWriter;
import javache.model.User;
import javache.repositories.IUserRepository;
import javache.repositories.UserRepository;
import javache.server.Server;

import java.io.IOException;

import static javache.utils.WebConstants.PORT;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class StartUp {

    public static void main(String[] args) throws IOException {
        ConsoleWriter outputWriter = new ConsoleOutputWriter();
        IUserRepository<User> userRepository = new UserRepository();
        Server server = new Server(PORT, outputWriter, userRepository);
        server.run();
    }
}
