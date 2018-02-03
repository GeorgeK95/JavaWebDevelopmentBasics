package main.java.app.javache.handlers;

import main.java.app.javache.Application;
import main.java.app.javache.handlers.contracts.IRequestHandler;
import main.java.app.javache.http.HttpContext;
import main.java.app.javache.http.HttpRequest;
import main.java.app.javache.http.HttpResponse;
import main.java.app.javache.http.contracts.IHttpContext;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;

import java.io.IOException;


/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class RequestHandler implements IRequestHandler {
    private IHttpContext httpContext;
    private Application application;

    public RequestHandler(Application application) {
        this.application = application;
//        this.initializeData();
    }

    @Override
    public byte[] handleRequest(String requestContent) throws IOException {
        IHttpRequest request = new HttpRequest(requestContent);
        IHttpResponse response = new HttpResponse();
        this.httpContext = new HttpContext(request, response);
        return this.application.handleRequest(this.httpContext);
    }
/*
    private void constructResponse() throws IOException {
        String requestMethod = this.request.getMethod();
        String url = this.request.getUrl();

        if (requestMethod.equals(GET)) {
            this.handleGetRequest(url);
        } else if (requestMethod.endsWith(POST)) {
            this.handlePostRequest(url);
        }

    }

    private void tryRegister(User user) {
        if (!user.getPassword().equals(user.getConfirm())) {
            this.BadRequest();
            return;
        }
        if (!this.userRepository.exists(user)) {
            this.userRepository.persist(user);
        } else {
            this.BadRequest();
        }
    }

    private void handleGetRequest(String url) {
        switch (url) {
            case USERS_PROFILE_PAGE:
                this.handleEvent(PATH_TO_PROFILE_PAGE, true, LOGIN, View.PROFILE);
                break;
            case HOME:
                this.handleEvent(PATH_TO_HOME_PAGE, true, LOGIN, View.HOME);
                break;
            case REGISTER:
                this.handleEvent(PATH_TO_REGISTER_PAGE, false, USERS_PROFILE_PAGE, null);
                break;
            case LOGIN:
                this.handleEvent(PATH_TO_LOGIN_PAGE, false, USERS_PROFILE_PAGE, null);
                break;
            case EMPTY:
                url = HTML_CONTENT_TYPE_STRING + "\\" + INDEX;
                this.Ok();
                this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
                break;
            case SERVER_PNG:
                this.Ok();
                this.response.addHeader(PNG_EXTENSION_STRING, this.contentTypeLines.get(PNG_EXTENSION_STRING));
                break;
            case BOOTSTRAP_MIN_CSS:
                this.Ok();
                this.response.addHeader(CSS_CONTENT_TYPE_STRING, this.contentTypeLines.get(CSS_CONTENT_TYPE_STRING));
                break;
            default:
                url = ERROR_404;
                this.NotFound();
                this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
                break;
        }

        this.setResponseContent(url, null);
    }

    private void handleEvent(String resourceAbsolutePath, boolean isAuthenticationNeeded, String redirectTo, View view) {
        Response pageContent;
        if (isAuthenticationNeeded) {
            pageContent = this.handleAuthenticatedPageRequest(resourceAbsolutePath, view);
        } else {
            pageContent = this.handleUnauthenticatedPageRequest();
        }
        if (pageContent == Response.RESPONSE_UNAUTHORIZED) {
            this.Unauthorized();
        } else if (pageContent == Response.RESPONSE_MOVED_PERMANENTLY) {
            this.Redirect(redirectTo);
        } else {
            this.Ok();
        }

        this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
    }

    private Response handleUnauthenticatedPageRequest() {
        try {
            String userId = this.getUserId();
            User user = this.userRepository.getById(userId);
            if (user != null) {
                byte[] profilePageContent = this.readAndGetTargetResource(PROFILE);
                this.setResponseContent(null, profilePageContent);
                return Response.RESPONSE_MOVED_PERMANENTLY;
            } else {
                return Response.RESPONSE_UNAUTHORIZED;
            }

        } catch (NullPointerException npe) {
            return Response.RESPONSE_OK;
        }
    }

    private String getUserId() {
        if (this.request.getCookies().containsKey(JAVACHE_SESSION_ID)) {
            String sessionId = this.request.getCookies().get(JAVACHE_SESSION_ID);
            return (String) this.session.getSingleSessionData(sessionId).get(USER_ID);
        }
        return null;
    }

    private Response handleAuthenticatedPageRequest(String resourceAbsolutePath, View view) {
        try {
            String userId = this.getUserId();
            User user = this.userRepository.getById(userId);
            if (user != null) {
                String generateProfileView = null;
                switch (view) {
                    case HOME:
                        List<String> otherUsersEmails = this.userRepository.getAll().stream().map(User::getEmail)
                                .filter(e -> !e.equals(this.userRepository.getById(this.getUserId()).getEmail()))
                                .sorted()
                                .collect(Collectors.toList());
                        generateProfileView = ViewGenerator.generateHomeView(resourceAbsolutePath,
                                otherUsersEmails.toArray(new String[otherUsersEmails.size()]));
                        break;
                    case PROFILE:
                        generateProfileView = ViewGenerator.generateProfileView(resourceAbsolutePath, user.getEmail(), user.getPassword());
                        break;
                }
                this.setResponseContent(null, generateProfileView.getBytes());
                return Response.RESPONSE_OK;
            } else {
                return Response.RESPONSE_UNAUTHORIZED;
            }

        } catch (NullPointerException npe) {
            return Response.RESPONSE_MOVED_PERMANENTLY;
        }
    }

    private void handlePostRequest(String url) {
        byte[] htmlContent;

        switch (url) {
            case REGISTER:
                User user = UserFactory.create(this.request.getBodyParams());
                this.tryRegister(user);
                this.Redirect(LOGIN);
                break;
            case LOGIN:
                this.tryLogInUser();
                break;
            case LOGOUT:
                this.Redirect(EMPTY);
                this.tryLogOutUser();
                break;
        }

        htmlContent = this.readAndGetTargetResource(url);
        if (htmlContent != null)
            this.response.setContent(htmlContent);
    }

    private void Redirect(String redirectTo) {
        this.response.setStatusCode(Response.RESPONSE_MOVED_PERMANENTLY.getCode());
        this.response.addHeader(Response.RESPONSE_MOVED_PERMANENTLY.getKey(), Response.RESPONSE_MOVED_PERMANENTLY.getValue());
        this.response.addHeader(LOCATION, LOCATION.concat("/" + redirectTo));
    }

    private void BadRequest() {
        this.response.setStatusCode(Response.BAD_REQUEST.getCode());
        this.response.addHeader(Response.BAD_REQUEST.getKey(), Response.BAD_REQUEST.getValue());
    }

    private void NotFound() {
        this.response.setStatusCode(Response.RESPONSE_NOT_FOUND.getCode());
        this.response.addHeader(Response.RESPONSE_NOT_FOUND.getKey(), Response.RESPONSE_NOT_FOUND.getValue());
        this.seedCommonDataToResponse();
    }

    private void Unauthorized() {
        this.response.setStatusCode(Response.RESPONSE_UNAUTHORIZED.getCode());
        this.response.addHeader(Response.RESPONSE_UNAUTHORIZED.getKey(), Response.RESPONSE_UNAUTHORIZED.getValue());
    }

    private void Ok() {
        this.response.setStatusCode(Response.RESPONSE_OK.getCode());
        this.response.addHeader(Response.RESPONSE_OK.getKey(), Response.RESPONSE_OK.getValue());
        this.seedCommonDataToResponse();
    }

    private void seedCommonDataToResponse() {
        this.response.addHeader(ServerConstants.SERVER_STRING, SERVER_HEADER_NAME_AND_VERSION);
        this.response.addHeader(INLINE_STRING, this.contentTypeLines.get(INLINE_STRING));
        this.response.addHeader(DATE, this.contentTypeLines.get(DATE));
    }

    private void setResponseContent(String url, byte[] readPageContent) {
        if (this.response.getContent() == null) {
            if (readPageContent == null)
                readPageContent = this.readAndGetTargetResource(url);
            this.response.setContent(readPageContent);
        }
    }

    private void tryLogOutUser() {
        if (this.request.getCookies().containsKey(JAVACHE_SESSION_ID)) {
            String sessionId = this.request.getCookies().get(JAVACHE_SESSION_ID);
            this.session.removeSession(sessionId);
        }
    }

    private void tryLogInUser() {
        String targetEmail = this.request.getBodyParams().get(EMAIL);
        String targetPassword = this.request.getBodyParams().get(PASSWORD);
        User foundUser = this.userRepository.findByEmail(targetEmail);

        if (foundUser != null) {
            if (foundUser.getPassword().equals(targetPassword)) {
                //successfully logged in
                String sessionId = UUID.randomUUID().toString();
                this.session.setSession(
                        sessionId,
                        new HashMap<String, Object>() {{
                            put(ServerConstants.USER_ID, foundUser.getId());
                        }});

                this.Redirect(USERS_PROFILE_PAGE);
                this.response.addSession(JAVACHE_SESSION_ID, sessionId);
            } else {
                this.BadRequest();
            }
        } else {
            this.BadRequest();
        }
    }

    private byte[] readAndGetTargetResource(String targetResource) {
        return InputStreamReader.readResourceAndGetHtmlContent(targetResource);
    }

    private void initializeData() {
        this.setContentTypes();
    }

    private void setContentTypes() {
        this.contentTypeLines = new HashMap<>();
        this.contentTypeLines.put(HTML_CONTENT_TYPE_STRING, CONTENT_TYPE_STRING.concat("text/html"));
        this.contentTypeLines.put(LOCATION, LOCATION.concat("/" + LOGIN));
        this.contentTypeLines.put(CSS_CONTENT_TYPE_STRING, CONTENT_TYPE_STRING.concat("text/css"));
        this.contentTypeLines.put(CSS_MAP_CONTENT_TYPE_STRING, CONTENT_TYPE_STRING.concat("casebook/json"));
        this.contentTypeLines.put(PNG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/png"));
        this.contentTypeLines.put(JPEG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/jpeg"));
        this.contentTypeLines.put(JPG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/jpg"));
        this.contentTypeLines.put(INLINE_STRING, CONTENT_DISPOSITION_STRING.concat("inline"));
        this.contentTypeLines.put(DATE, DATE.concat(new Date().toString()));
    }*/
}