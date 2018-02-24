package utils;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public final class Constants {

    //routes
    public static final String HOME = "/";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String KITTENS_ALL = "/kittens/all";
    public static final String KITTENS_ADD = "/kittens/add";

    //templates
    public static final String TEMPLATE_LOGGED_IN = "template:loggedIn";
    public static final String TEMPLATE_REGISTER = "template:register";
    public static final String TEMPLATE_LOGIN = "template:login";
    public static final String TEMPLATE_INDEX = "template:index";
    public static final String TEMPLATE_ALL_KITTENS = "template:all-kittens";
    public static final String TEMPLATE_ADD_KITTEN = "template:add-kitten";

    //redirects
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_HOME = "redirect:/";
    public static final String REDIRECT_REGISTER = "redirect:/register";

    //others
    public static final String USER_ID = "user-id";
    public static final String USERNAME = "username";

    //
    public static final String LOGGED_NAVIGATION_STRING = "LOGGED_NAVIGATION";
    public static final String GUEST_NAVIGATION_STRING = "GUEST_NAVIGATION";
    public static final String HEAD_STRING = "HEAD";
    public static final String FOOTER_STRING = "FOOTER";
    public static final String ALL_KITTENS = "ALL_KITTENS";

    //html
    public static final String GUEST_NAVIGATION = "<nav class=\"navbar navbar-toggleable-md navbar-light bg-faded\">\n" +
            "        <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\"\n" +
            "                data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\"\n" +
            "                aria-label=\"Toggle navigation\">\n" +
            "            <span class=\"navbar-toggler-icon\"></span>\n" +
            "        </button>\n" +
            "        <a class=\"navbar-brand\" href=\"/\">FDMC</a>\n" +
            "\n" +
            "        <div class=\"collapse navbar-collapse justify-content-end row\" id=\"navbarSupportedContent\">\n" +
            "            <ul class=\"navbar-nav ml-auto\">\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link\" href=\"/\">Home</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link\" href=\"/login\">Login</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link\" href=\"/register\">Register</a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "    </nav>";

    public static final String LOGGED_NAVIGATION = "<nav class=\"navbar navbar-toggleable-md navbar-light bg-faded\">\n" +
            "        <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\"\n" +
            "                data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\"\n" +
            "                aria-label=\"Toggle navigation\">\n" +
            "            <span class=\"navbar-toggler-icon\"></span>\n" +
            "        </button>\n" +
            "        <a class=\"navbar-brand\" href=\"/\">FDMC</a>\n" +
            "        <div class=\"collapse navbar-collapse justify-content-end row\" id=\"navbarSupportedContent\">\n" +
            "            <ul class=\"navbar-nav ml-auto justify-content-end row col-md-8\">\n" +
            "                <li class=\"nav-item active col-md-2\">\n" +
            "                    <a class=\"nav-link\" href=\"/\">Home</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-2\">\n" +
            "                    <a class=\"nav-link\" href=\"/kittens/all\">All Kittens</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-2\">\n" +
            "                    <a class=\"nav-link\" href=\"/kittens/add\">Add Kitten</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-2\">\n" +
            "                    <a class=\"nav-link\" href=\"/logout\">Logout</a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "    </nav>";

    public static final String FOOTER = "<footer class=\"footer\">\n" +
            "        <div class=\"container-fluid\">\n" +
            "            <div class=\"text-muted container-fluid text-center\">&copy; CopyRight Sanity Web Design Studios 2018. All\n" +
            "                rights\n" +
            "                reserved.\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </footer>";
    public static final String HEAD = "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>FDMC</title>\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\">\n" +
            "</head>";

    private Constants() {
    }
}
