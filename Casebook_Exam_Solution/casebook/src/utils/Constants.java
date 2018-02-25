package utils;

public final class Constants {

    //paths
    public static final String HEAD_ABSOLUTE_PARH = "C:\\Users\\George-Lenovo\\Desktop\\EXAM\\Solution\\casebook\\src\\resources\\templates\\fragments\\head.html";
    public static final String GUEST_NAVIGATION_ABSOLUTE_PARH = "C:\\Users\\George-Lenovo\\Desktop\\EXAM\\Solution\\casebook\\src\\resources\\templates\\fragments\\guest_navigation.html";
    public static final String LOGGED_NAVIGATION_ABSOLUTE_PARH = "C:\\Users\\George-Lenovo\\Desktop\\EXAM\\Solution\\casebook\\src\\resources\\templates\\fragments\\logged_navigation.html";
    public static final String FOOTER_ABSOLUTE_PARH = "C:\\Users\\George-Lenovo\\Desktop\\EXAM\\Solution\\casebook\\src\\resources\\templates\\fragments\\footer.html";

    //routes
    public static final String INDEX = "/";
    public static final String HOME = "/home";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String PROFILE_DETAILS = "/profile/{id}";
    public static final String FRIENDS_ROUTE = "/friends";
    public static final String FRIENDS_ADD = "/friends/add/{id}";
    public static final String FRIENDS_REMOVE_ID = "/friends/remove/{id}";

    //templates
    public static final String TEMPLATE_REGISTER = "template:register";
    public static final String TEMPLATE_PROFILE = "template:profile";
    public static final String TEMPLATE_LOGIN = "template:login";
    public static final String TEMPLATE_INDEX = "template:index";
    public static final String TEMPLATE_HOME = "template:home";
    public static final String TEMPLATE_FRIENDS = "template:friends";

    //redirects
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_INDEX = "redirect:/";
    public static final String REDIRECT_HOME = "redirect:/home";
    public static final String REDIRECT_FRIENDS = "redirect:/friends";
    public static final String REDIRECT_REGISTER = "redirect:/register";

    //others
    public static final String USER_ID = "user-id";
    public static final String USERNAME = "username";
    public static final String GENDER = "gender";
    public static final String ALL_USERS = "allUsers";
    public static final String CURRENT_USER_ID = "currentUserId";
    public static final String ERROR = "error";
    public static final String DISPLAY = "display";
    public static final String TYPE = "type";
    public static final String DANGER = "danger";
    public static final String INCORRECT_USERNAME_OR_PASSWORD_MESSAGE = "Incorrect username or password.";
    public static final String STYLE_DISPLAY_BLOCK = "style=\"display: block\"";
    public static final String STYLE_DISPLAY_NONE = "style=\"display: none\"";
    public static final String FRIENDS = "FRIENDS";
    public static final String FRIENDS_TABLE = "friendsTable";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final String PICTURE = "picture";

    //fragments
    public static final String LOGGED_NAVIGATION_STRING = "LOGGED_NAVIGATION";
    public static final String GUEST_NAVIGATION_STRING = "GUEST_NAVIGATION";
    public static final String HEAD_STRING = "HEAD";
    public static final String FOOTER_STRING = "FOOTER";

    //html
    public static final String GUEST_NAVIGATION = "<nav class=\"navbar navbar-toggleable-md navbar-dark bg-color-dark\">\n" +
            "        <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\"\n" +
            "                data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\"\n" +
            "                aria-label=\"Toggle navigation\">\n" +
            "            <span class=\"navbar-toggler-icon\"></span>\n" +
            "        </button>\n" +
            "        <a class=\"navbar-brand text-muted h4\" href=\"/\">Casebook</a>\n" +
            "\n" +
            "        <div class=\"collapse navbar-collapse justify-content-end row\" id=\"navbarSupportedContent\">\n" +
            "            <ul class=\"navbar-nav ml-auto\">\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-muted h5\" href=\"/\">Home</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-muted h5\" href=\"/login\">Login</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-muted h5\" href=\"/register\">Register</a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "    </nav>";

    public static final String LOGGED_NAVIGATION = "<nav class=\"navbar navbar-toggleable-md navbar-dark bg-color-dark\">\n" +
            "        <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\"\n" +
            "                data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\"\n" +
            "                aria-label=\"Toggle navigation\">\n" +
            "            <span class=\"navbar-toggler-icon\"></span>\n" +
            "        </button>\n" +
            "        <a class=\"navbar-brand text-muted h4\" href=\"/home\">Casebook</a>\n" +
            "\n" +
            "        <div class=\"collapse navbar-collapse justify-content-end row\" id=\"navbarSupportedContent\">\n" +
            "            <ul class=\"navbar-nav ml-auto\">\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-muted h5\" href=\"/home\">Home</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-muted h5\" href=\"/friends\">Friends</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-muted h5\" href=\"/logout\">Logout</a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "    </nav>";

    public static final String FOOTER = "<footer class=\"footer py-3\">\n" +
            "        <div class=\"container-fluid\">\n" +
            "            <div class=\"text-muted container-fluid text-center\">&copy; CopyRight Sanity Web Design Studios 2018. All rights reserved.</div>\n" +
            "        </div>\n" +
            "    </footer>";


    public static final String HEAD = "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Casebook</title>\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\">\n" +
            "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n" +
            "</head>";

    private Constants() {
    }
}