package javache.handlers;

import javache.enums.Response;
import javache.factories.UserFactory;
import javache.http.HttpRequest;
import javache.http.HttpResponse;
import javache.http.contracts.IHttpRequest;
import javache.http.contracts.IHttpResponse;
import javache.http.contracts.IHttpSession;
import javache.io.InputStreamReader;
import javache.model.User;
import javache.repositories.IUserRepository;
import javache.utils.DynamicPageLoader;
import javache.utils.WebConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javache.utils.WebConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class RequestHandler {

    private static final String JAVACHE_SESSION_ID = "JAVACHE_SESSION_ID";
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
            case PROFILE:
                this.handleProfilePageRequest();
                break;
            case EMPTY:
                url = INDEX;
            case REGISTER:
            case LOGIN:
            case INDEX:
                this.Ok(this.readAndGetTargetResource(url));
                this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
                break;
            case SERVER_PNG:
                this.Ok(this.readAndGetTargetResource(url));
                this.response.addHeader(PNG_EXTENSION_STRING, this.contentTypeLines.get(PNG_EXTENSION_STRING));
                break;
            case BOOTSTRAP_MIN_CSS:
                this.Ok(this.readAndGetTargetResource(url));
                this.response.addHeader(CSS_CONTENT_TYPE_STRING, this.contentTypeLines.get(CSS_CONTENT_TYPE_STRING));
                break;
            default:
                this.NotFound(this.readAndGetTargetResource(ERROR_404));
                this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
                break;
        }

    }

    private void handleProfilePageRequest() {
        if (this.request.getCookies().containsKey(JAVACHE_SESSION_ID)) {
            String sessionId = this.request.getCookies().get(JAVACHE_SESSION_ID);
            try {
                String userId = (String) this.session.getSingleSessionData(sessionId).get(USER_ID);
                User user = this.userRepository.getById(userId);
                if (user != null) {
                    String dynamicPageFormatted = DynamicPageLoader.formatDynamicPage(PATH_TO_LOGGED_PAGE, user.getEmail(), user.getPassword());
                    this.Ok(dynamicPageFormatted.getBytes());
                } else {
                    this.Unauthorized();
                }
            } catch (NullPointerException npe) {
                this.Ok(this.readAndGetTargetResource(GUEST));
            }

        } else {
            this.Ok(this.readAndGetTargetResource(GUEST));
        }
        this.response.addHeader(HTML_CONTENT_TYPE_STRING, this.contentTypeLines.get(HTML_CONTENT_TYPE_STRING));
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
                //TODO: implement sessions for next exercise, now its only with cookies :/
                this.tryLogInUser();
                break;
            case LOGOUT:
                //TODO: implement logout for next exercise when sessions are ready
                this.Redirect(INDEX);
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

    private void NotFound(byte[] htmlContent) {
        this.response.setStatusCode(Response.RESPONSE_NOT_FOUND.getCode());
        this.response.addHeader(Response.RESPONSE_NOT_FOUND.getKey(), Response.RESPONSE_NOT_FOUND.getValue());
        this.seedCommonDataToResponse(htmlContent);
    }

    private void Unauthorized() {
        this.response.setStatusCode(Response.RESPONSE_UNAUTHORIZED.getCode());
        this.response.addHeader(Response.RESPONSE_UNAUTHORIZED.getKey(), Response.RESPONSE_UNAUTHORIZED.getValue());
//        this.seedCommonDataToResponse(htmlContent);
    }

    private void Ok(byte[] htmlContent) {
        this.response.setStatusCode(Response.RESPONSE_OK.getCode());
        this.response.addHeader(Response.RESPONSE_OK.getKey(), Response.RESPONSE_OK.getValue());
        this.seedCommonDataToResponse(htmlContent);
    }

    private void seedCommonDataToResponse(byte[] htmlContent) {
        this.response.addHeader(WebConstants.SERVER_STRING, SERVER_HEADER_NAME_AND_VERSION);
        this.response.addHeader(INLINE_STRING, this.contentTypeLines.get(INLINE_STRING));
        this.setResponseContent(htmlContent);
    }

    private void setResponseContent(byte[] htmlContent) {
        if (this.response.getContent() == null)
            this.response.setContent(htmlContent);
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

                this.Redirect(PROFILE);
                this.response.addCookie(JAVACHE_SESSION_ID, sessionId);
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
        this.contentTypeLines.put(CSS_MAP_CONTENT_TYPE_STRING, CONTENT_TYPE_STRING.concat("application/json"));
        this.contentTypeLines.put(PNG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/png"));
        this.contentTypeLines.put(JPEG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/jpeg"));
        this.contentTypeLines.put(JPG_EXTENSION_STRING, CONTENT_TYPE_STRING.concat("image/jpg"));
        this.contentTypeLines.put(INLINE_STRING, CONTENT_DISPOSITION_STRING.concat("inline"));
    }
}