package bookhut.repositories.factories;

import static bookhut.utils.Constants.*;

public class FactoryProducer {

    public static AbstractRepoFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase(USER)) {
            return new UserRepositoryFactory();

        } else if (choice.equalsIgnoreCase(BOOK)) {
            return new BookRepositoryFactory();
        }

        return null;
    }
}