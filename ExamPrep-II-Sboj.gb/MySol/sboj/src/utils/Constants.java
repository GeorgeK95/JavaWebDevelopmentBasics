package utils;

public final class Constants {

    //routes
    public static final String INDEX = "/";
    public static final String HOME = "/home";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String JOBS_DETAILS = "/jobs/details/{id}";
    public static final String JOBS_DELETE = "/jobs/delete/{id}";

    //templates
    public static final String TEMPLATE_REGISTER = "template:register";
    public static final String TEMPLATE_LOGIN = "template:login";
    public static final String TEMPLATE_INDEX = "template:index";
    public static final String TEMPLATE_HOME = "template:home";
    public static final String TEMPLATE_ADD_JOB = "template:add-job";
    public static final String TEMPLATE_JOB_DETAILS = "template:job-details";
    public static final String TEMPLATE_JOB_DELETE = "template:job-delete";
    public static final String JOBS_ADD = "/jobs/add";

    //redirects
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_INDEX = "redirect:/";
    public static final String REDIRECT_HOME = "redirect:/home";
    public static final String REDIRECT_REGISTER = "redirect:/register";

    //others
    public static final String USER_ID = "user-id";
    public static final String USERNAME = "username";
    public static final String ID = "id";
    public static final String SECTOR = "sector";
    public static final String PROFESSION = "profession";
    public static final String SALARY = "salary";
    public static final String DESCRIPTION = "description";
    public static final String JOB_APPLICATIONS = "jobApplications";
    public static final String ERROR = "error";
    public static final String DISPLAY = "display";
    public static final String TYPE = "type";
    public static final String DANGER = "danger";
    public static final String INCORRECT_USERNAME_OR_PASSWORD_MESSAGE = "Incorrect username or password.";
    public static final String STYLE_DISPLAY_BLOCK = "style=\"display: block\"";
    public static final String STYLE_DISPLAY_NONE = "style=\"display: none\"";

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
            "        <a class=\"navbar-brand text-info h4\" href=\"/\">Sboj.gb</a>\n" +
            "\n" +
            "        <div class=\"collapse navbar-collapse justify-content-end row\" id=\"navbarSupportedContent\">\n" +
            "            <ul class=\"navbar-nav ml-auto\">\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-info h5\" href=\"/\">Home</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-info h5\" href=\"/login\">Login</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item active col-md-4\">\n" +
            "                    <a class=\"nav-link text-info h5\" href=\"/register\">Register</a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "    </nav>";

    public static final String LOGGED_NAVIGATION = "<nav class=\"navbar navbar-toggleable-md navbar-dark bg-color-dark\">\n" +
            "    <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\"\n" +
            "            data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\"\n" +
            "            aria-label=\"Toggle navigation\">\n" +
            "        <span class=\"navbar-toggler-icon\"></span>\n" +
            "    </button>\n" +
            "    <a class=\"navbar-brand text-info h4\" href=\"/home\">Sboj.gb</a>\n" +
            "\n" +
            "    <div class=\"collapse navbar-collapse row\" id=\"navbarSupportedContent\">\n" +
            "        <ul class=\"navbar-nav ml-auto row col-md-7 justify-content-end\">\n" +
            "            <li class=\"nav-item active col-md-3\">\n" +
            "                <a class=\"nav-link text-info h5\" href=\"/home\">Home</a>\n" +
            "            </li>\n" +
            "            <li class=\"nav-item active col-md-3\">\n" +
            "                <a class=\"nav-link text-info h5\" href=\"/jobs/add\">Add Job</a>\n" +
            "            </li>\n" +
            "            <li class=\"nav-item active col-md-3\">\n" +
            "                <a class=\"nav-link text-info h5\" href=\"/logout\">Logout</a>\n" +
            "            </li>\n" +
            "        </ul>\n" +
            "    </div>\n" +
            "</nav>";

    public static final String FOOTER = "<footer class=\"footer\">\n" +
            "        <div class=\"container-fluid\">\n" +
            "            <div class=\"text-muted container-fluid text-center\">&copy; CopyRight Sanity Web Design Studios 2018. All rights reserved.</div>\n" +
            "        </div>\n" +
            "    </footer>";


    public static final String HEAD = "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Sboj.gb</title>\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\">\n" +
            "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n" +
            "</head>";

    private Constants() {
    }
}