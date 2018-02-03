package main.java.app.casebook.handlers.fixed;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.annotations.Post;
import main.java.app.casebook.enums.ContentType;
import main.java.app.javache.enums.Response;
import main.java.app.casebook.handlers.BaseHandler;
import main.java.app.database.annotations.Repository;
import main.java.app.database.entities.User;
import main.java.app.database.enums.UserProperty;
import main.java.app.database.repositories.user.IUserRepository;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;

import java.util.Map;

import static main.java.app.casebook.utilities.ApplicationConstants.LOGIN;
import static main.java.app.casebook.utilities.ApplicationConstants.REGISTER_HTML_PATH;
import static main.java.app.database.utils.DatabaseConstants.DISMISS_METHOD_NAME;
import static main.java.app.database.utils.DatabaseConstants.PERSIST_METHOD_NAME;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
@ApplicationRequestHandler
public class RegisterHandler extends BaseHandler {
    @Repository
    private IUserRepository<User> userRepository;

    @Get(route = "/html/register.html")
    public IHttpResponse buildResponse(IHttpRequest request, IHttpResponse response) {
        return super.handleEvent(request, response, Response.RESPONSE_OK, ContentType.HTML, REGISTER_HTML_PATH);
    }

    @Post(route = "/html/register.html")
    public IHttpResponse buildResponseProcess(IHttpRequest request, IHttpResponse response) {
        Map<String, String> userData = request.getBodyParams();
        String username = userData.get(UserProperty.EMAIL.name().toLowerCase());
        String password = userData.get(UserProperty.PASSWORD.name().toLowerCase());
        String confirm = userData.get(UserProperty.CONFIRM.name().toLowerCase());
        Response registrationProcess = this.tryToRegister(username, password, confirm);

        if (registrationProcess == Response.BAD_REQUEST)
            return super.handleEvent(request, response, Response.BAD_REQUEST, ContentType.HTML, REGISTER_HTML_PATH);
        else
            return super.handleEvent(request, response, Response.RESPONSE_MOVED_PERMANENTLY, ContentType.HTML,
                    REGISTER_HTML_PATH, LOGIN);
    }

    private Response tryToRegister(String email, String password, String confirm) {
        if (!password.equals(confirm)) {
            return Response.BAD_REQUEST;
        }
        Response response;
        if (!this.userRepository.exists(email)) {
            this.userRepository.invokeMethod(PERSIST_METHOD_NAME, email, password);
            response = Response.RESPONSE_MOVED_PERMANENTLY;
        } else {
            response = Response.BAD_REQUEST;
        }

        this.userRepository.invokeMethod(DISMISS_METHOD_NAME, email, password);
        return response;
    }

}
