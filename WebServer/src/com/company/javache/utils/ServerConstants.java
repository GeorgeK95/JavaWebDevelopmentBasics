package com.company.javache.utils;

import com.company.javache.StartUp;

import java.io.File;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class ServerConstants {
    //cookies constants
    public static final String COOKIE = "Cookie";
    public static final String SET_COOKIE = "Set-Cookie: ";
    public static final String USER_ID = "userId";
    public static final String JAVACHE_SESSION_ID = "JAVACHE_SESSION_ID";

    //config constants
    public static final String CONFIG_PATH = "config\\config.ini";
    public static final String CONFIG_FILE_DESTINATION = ServerConstants.SERVER_ROOT_FOLDER +
            CONFIG_PATH;
    public static final String HANDLER_SUFFIX = "Handler";

    //splitters
    public static final String AMPERSAND_SPLITTER = "\\&";
    public static final String PILE_SPLITTER = "|";
    public static final String COLON_SPLITTER = "\\:\\s";
    public static final String SEMICOLON_SPLITTER = "\\;\\s";
    public static final String EQUAL_SPLITTER = "=";
    public static final String HTML_SPACE_ENCODED_SYMBOL = "+";
    public static final String SPACE = " ";

    //Paths
    public static final String HANDLERS_PACKAGE_PATH = "main.java.app.casebook.handlers.";
    public static final String USERS_PROFILE_PAGE = "users/profile";
    public static final String STATIC_HANDLES_FOLDER = "static\\handlers";
    public static final String SERVER_ROOT_FOLDER = new File(StartUp.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toString().concat(File.separator);
    public static final String NOT_FOUND_HANDLER = "ResourceNotFoundHandler";

    //Server constants
    public static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;
    public static final int PORT = 3310;
    public static final String TIMEOUT_DETECTION_MESSAGE = "Waiting time has passed.";
    public static final String ESTABLISHED_CONNECTION = "Established connection.";
    public static final String WAITING_ON_PORT_D = "Waiting on port %d ...";
    public static final String SERVER_HEADER_NAME_AND_VERSION = "Server: Javache/-1.0.0";
    public static final String SERVER_STRING = "Server";
    public static final String CLASS_TYPE = ".class";
    public static final String JAR_TYPE = ".jar";
    public static final String EMPTY = "";
    public static final char DOT = '.';
    public static final String HANDLERS_DIRECTORY_RELOADED_MESSAGE = "Handlers directory reloaded.";

    private ServerConstants() {
    }
}
