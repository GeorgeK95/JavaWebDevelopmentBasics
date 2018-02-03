package com.company.run;

import com.company.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        ServerUtils serverUtils = new ServerUtils();
        Server server = new Server(3310);
        server.run();
    }
}
