package main.java.app.javache;

import main.java.app.database.repositories.DbRepository;
import main.java.app.javache.enums.RequestMethod;
import main.java.app.javache.http.contracts.IHttpContext;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface Application {
    Map<RequestMethod, Map<String, Function<IHttpContext, byte[]>>> getRoutes();

    IHttpSessionStorage getSession();

    void setRepository(DbRepository repository);

    void setSession(IHttpSessionStorage session);

    byte[] handleRequest(IHttpContext httpContext);

    void establishResources();

}
