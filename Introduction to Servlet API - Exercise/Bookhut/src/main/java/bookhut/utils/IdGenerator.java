package bookhut.utils;

/**
 * Created by George-Lenovo on 02/16/2018.
 */
public class IdGenerator {
    private static int nextId = 0;

    public static int generateId() {
        return ++IdGenerator.nextId;
    }
}
