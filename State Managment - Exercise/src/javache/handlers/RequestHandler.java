package javache.handlers;

import javache.enums.Response;
import javache.enums.View;
import javache.factories.UserFactory;
import javache.http.HttpRequest;
import javache.http.HttpResponse;
import javache.http.contracts.IHttpRequest;
import javache.http.contracts.IHttpResponse;
import javache.http.contracts.IHttpSession;
import javache.io.InputStreamReader;
import javache.model.User;
import javache.repositories.IUserRepository;
import javache.utils.ViewGenerator;
import javache.utils.WebConstants;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static javache.utils.WebConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class RequestHandler {

    private IHttpRequest request;
    private IHttpResponse response;

    private Map<String, String> contentTypeLines;
    private IUserRepository<User> userRepository;

    private IHttpSession session;

    public RequestHandler(IUserRepository<User> userRepository, IHttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
        this.initializeData();
    }

    byte[] handleRequest(String requestContent) throws IOException {
        this.request = new HttpRequest(requestContent);

        this.response = new HttpResponse();
        this.constructResponse();

        return response.getBytes();
    }

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
            this.badRequest();
            return;
        }
        if (!this.userRepository.exists(user)) {
            this.userRepository.persist(user);
        } else {
            this.badRequest();
        }
    }

    private void handleGetRequest(String url) {
        switch (url) {
            case USERS_PROFILE_PAGE:
                this.buildResponse(PATH_TO_PROFILE_PAGE, true, LOGIN, View.PROFILE);
                break;
            case HOME:
                this.buildResponse(PATH_TO_HOME_PAGE, true, LOGIN, View.HOME);
                break;
            case REGISTER:
                this.buildResponse(PATH_TO_REGISTER_PAGE, false, USERS_PROFILE_PAGE, null);
                break;
            case LOGIN:
                this.buildResponse(PATH_TO_LOGIN_PAGE, false, USERS_PROFILE_PAGE, null);
                break;
            case EMPTY:
                url = HTML_CONTENT_TYPE_STRING + "\\" + INDEX;
                this.ok();
                this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
                break;
            case SERVER_PNG:
                this.ok();
                this.response.addHeader(PNG_EXTENSION_STRING, this.contentTypeLines.get(PNG_EXTENSION_STRING));
                break;
            case BOOTSTRAP_MIN_CSS:
                this.ok();
                this.response.addHeader(CSS_CONTENT_TYPE_STRING, this.contentTypeLines.get(CSS_CONTENT_TYPE_STRING));
                break;
            default:
                url = ERROR_404;
                this.notFound();
                this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
                break;
        }

        this.setResponseContent(url, null);
    }

    private void buildResponse(String resourceAbsolutePath, boolean isAuthenticationNeeded, String redirectTo, View view) {
        Response pageContent;
        if (isAuthenticationNeeded) {
            pageContent = this.handleAuthenticatedPageRequest(resourceAbsolutePath, view);
        } else {
            pageContent = this.handleUnauthenticatedPageRequest();
        }
        if (pageContent == Response.RESPONSE_UNAUTHORIZED) {
            this.unauthorized();
        } else if (pageContent == Response.RESPONSE_MOVED_PERMANENTLY) {
            this.redirect(redirectTo);
        } else {
            this.ok();
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
                this.redirect(LOGIN);
                break;
            case LOGIN:
                this.tryLogInUser();
                break;
            case LOGOUT:
                this.redirect(EMPTY);
                this.tryLogOutUser();
                break;
        }

        htmlContent = this.readAndGetTargetResource(url);
        if (htmlContent != null)
            this.response.setContent(htmlContent);
    }

    private void redirect(String redirectTo) {
        this.response.setStatusCode(Response.RESPONSE_MOVED_PERMANENTLY.getCode());
        this.response.addHeader(Response.RESPONSE_MOVED_PERMANENTLY.getKey(), Response.RESPONSE_MOVED_PERMANENTLY.getValue());
        this.response.addHeader(LOCATION, LOCATION.concat("/" + redirectTo));
    }

    private void badRequest() {
        this.response.setStatusCode(Response.BAD_REQUEST.getCode());
        this.response.addHeader(Response.BAD_REQUEST.getKey(), Response.BAD_REQUEST.getValue());
    }

    private void notFound() {
        this.response.setStatusCode(Response.RESPONSE_NOT_FOUND.getCode());
        this.response.addHeader(Response.RESPONSE_NOT_FOUND.getKey(), Response.RESPONSE_NOT_FOUND.getValue());
        this.seedCommonDataToResponse();
    }

    private void ok() {
        this.response.setStatusCode(Response.RESPONSE_OK.getCode());
        this.response.addHeader(Response.RESPONSE_OK.getKey(), Response.RESPONSE_OK.getValue());
        this.seedCommonDataToResponse();
    }

    private void seedCommonDataToResponse() {
        this.response.addHeader(WebConstants.SERVER_STRING, SERVER_HEADER_NAME_AND_VERSION);
        this.response.addHeader(INLINE_STRING, this.contentTypeLines.get(INLINE_STRING));
        this.response.addHeader(DATE, this.contentTypeLines.get(DATE));
    }

    private void unauthorized() {
        this.response.setStatusCode(Response.RESPONSE_UNAUTHORIZED.getCode());
        this.response.addHeader(Response.RESPONSE_UNAUTHORIZED.getKey(), Response.RESPONSE_UNAUTHORIZED.getValue());
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
                            put(WebConstants.USER_ID, foundUser.getId());
                        }});

                this.redirect(USERS_PROFILE_PAGE);
                this.response.addCookie(JAVACHE_SESSION_ID, sessionId);
            } else {
                this.badRequest();
            }
        } else {
            this.badRequest();
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
        this.contentTypeLines.put(CSS_MAP_CONTENT_TYPE_STRING, CONTENT_TYPE_STRING.concat("application/json"));
        this.contentTypeLines.put(PNG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/png"));
        this.contentTypeLines.put(JPEG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/jpeg"));
        this.contentTypeLines.put(JPG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/jpg"));
        this.contentTypeLines.put(INLINE_STRING, CONTENT_DISPOSITION_STRING.concat("inline"));
        this.contentTypeLines.put(DATE, DATE.concat(new Date().toString()));
    }
}