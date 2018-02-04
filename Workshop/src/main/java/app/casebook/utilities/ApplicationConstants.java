package main.java.app.casebook.utilities;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class ApplicationConstants {
    //Paths
    private static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    public static final String HOME_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\pages\\home\\home.html");
    public static final String PROFILE_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\pages\\users\\profile.html");
    public static final String BOOTSTRAP_CSS_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\css\\bootstrap.min.css");
    public static final String INDEX_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\index.html");
    public static final String BUNDLE_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\fragments\\base-layout.html");
    public static final String HEAD_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\fragments\\head.html");
    public static final String HEADER_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\fragments\\header.html");
    public static final String FOOTER_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\fragments\\footer.html");
    public static final String LOGIN_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\login.html");
    public static final String IMAGE_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\images\\server.png");
    public static final String ERROR_404_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\error-404.html");
    public static final String REGISTER_HTML_PATH = PROJECT_DIRECTORY.concat("\\src\\main\\java\\app\\casebook\\resources\\assets\\html\\register.html");

    //view constants
    public static final String HEAD_TEMPLATE = "${head}";
    public static final String HEADER_TEMPLATE = "${header}";
    public static final String VIEW_TEMPLATE = "${view}";
    public static final String FOOTER_TEMPLATE = "${footer}";

    //Constants
    public static final String EMPTY = "";
    public static final String BUILD_RESPONSE = "buildResponse";
    public static final String BUILD_RESPONSE_PROCESS = BUILD_RESPONSE + "Process";
    public static final String LOCATION = "Location: ";
    public static final String INLINE_STRING = "inline";
    public static final String DATE = "Date: ";
    public static final String LOGIN = "html/login.html";
    public static final String INDEX = EMPTY;
    public static final String PROFILE = "users/profile";
    public static final String CONTENT_DISPOSITION_STRING = "Content-Disposition: ";
}
