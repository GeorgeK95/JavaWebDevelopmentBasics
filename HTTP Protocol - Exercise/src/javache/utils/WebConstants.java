package javache.utils;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class WebConstants {
    //paths
    public static String RESOURCE_FOLDER = "\\src\\resources\\";
    //    public static String PAGES = "pages\\";
    public static String ASSETS = "assets\\";
    public static String PAGES = "pages\\";
    public static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    public static final String DB_FILE = PROJECT_DIRECTORY + RESOURCE_FOLDER + "database\\database.txt";
    //    static final String DB_FILE = PROJECT_DIRECTORY + DB_PATH;
    public static final String ERROR_404 = "error-404";

    //requests types
    public static final String GET = "GET";
    public static final String POST = "POST";

    //string types
    public static final String HTML_CONTENT_TYPE_STRING = "html";
    public static final String PNG_EXTENSION_STRING = "png";
    public static final String JPEG_EXTENSION_STRING = "jpeg";
    public static final String JPG_EXTENSION_STRING = "jpg";
    //    public static final String PLAIN_CONTENT_TYPE_STRING = "plain";
    public static final String IMAGE_STRING = "images";
    public static final String CSS_CONTENT_TYPE_STRING = "css";
    public static final String CSS_MAP_CONTENT_TYPE_STRING = "map";
    public static final String INLINE_STRING = "inline";
    public static final String CONTENT_DISPOSITION_STRING = "Content-Disposition: ";
    public static final String CONTENT_TYPE_STRING = "Content-Type: ";
    public static final String LOCATION = "Location: ";
    public static final String INDEX = "index";
    public static final String BOOTSTRAP_MIN_CSS = "css/bootstrap.min.css";

    //extensions
    static final String DOT = ".";
    public static final String HTML_EXTENSION = DOT + HTML_CONTENT_TYPE_STRING;
    private static final String PNG_EXTENSION = DOT + PNG_EXTENSION_STRING;

    //pages
    private static final String SERVER = "server";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String REGISTER = "register";
    public static final String PROFILE = "profile";
    public static final String SERVER_PNG = SERVER + PNG_EXTENSION;
    private static final String LOGGED = "logged";
    public static final String PATH_TO_LOGGED_PAGE = PROJECT_DIRECTORY + RESOURCE_FOLDER + PAGES + PROFILE + "\\" + LOGGED + DOT + HTML_CONTENT_TYPE_STRING;
    public static final String GUEST = "guest";

    //service constants
    public static final String SPACE = "\\s+";
    public static final String EMPTY = "";
    public static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;
    public static final int PORT = 3310;
    public static final String TIMEOUT_DETECTION_MESSAGE = "Waiting time has passed.";
    public static final String ESTABLISHED_CONNECTION = "Established connection.";
    public static final String WAITING_ON_PORT_D = "Waiting on port %d ...";
    public static final String SERVER_HEADER_NAME_AND_VERSION = "Server: Javache/-1.0.0";
    public static final String SERVER_STRING = "Server";
    public static final String HTML_SPACE_ENCODED_SYMBOL = "+";
    public static final String NORMAL_SPACE = " ";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ID = "id";
    public static final String CONFIRM = "confirm";
    public static final String AMPERSAND_SPLITTER = "\\&";
    public static final String PILE_SPLITTER = "|";
    public static final String COLON_SPLITTER = "\\:\\s";
    public static final String SEMICOLON_SPLITTER = "\\;\\s";
    public static final String EQUAL_SPLITTER = "=";

    //cookies constants
    public static final String COOKIE = "Cookie";
    public static final String SET_COOKIE = "Set-Cookie: ";
    public static final String USER_ID = "userId";

    private WebConstants() {
    }
}
