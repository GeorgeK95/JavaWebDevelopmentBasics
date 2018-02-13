package com.company.javache;

import com.company.javache.server.Server;

import java.io.IOException;

import static com.company.javache.utils.ServerConstants.PORT;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class StartUp {

    public static void main(String[] args) throws IOException {
        Server server = new Server(PORT);
        server.run();
    }
}
