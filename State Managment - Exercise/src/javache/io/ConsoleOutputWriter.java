package javache.io;

import javache.io.contracts.ConsoleWriter;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ConsoleOutputWriter implements ConsoleWriter {

    @Override
    public void writeOnConsole(String output) {
        System.out.println(output);
    }
}
