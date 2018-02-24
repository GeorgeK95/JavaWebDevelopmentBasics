package factories;

import repositories.KittenRepository;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public final class KittenRepositoryFactory {
    public static KittenRepository create() {
        return new KittenRepository();
    }
}
