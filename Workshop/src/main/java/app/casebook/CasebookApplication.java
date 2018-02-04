package main.java.app.casebook;

import main.java.app.casebook.handlers.fixed.PageNotFoundHandler;
import main.java.app.casebook.utilities.loaders.ApplicationRoutesLoader;
import main.java.app.casebook.utilities.loaders.HandlerLoader;
import main.java.app.database.repositories.DbRepository;
import main.java.app.javache.Application;
import main.java.app.javache.enums.RequestMethod;
import main.java.app.javache.http.contracts.IHttpContext;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class CasebookApplication implements Application {
    private Map<RequestMethod, Map<String, Function<IHttpContext, byte[]>>> routes;
    private IHttpSessionStorage session;
    private DbRepository repository;

    public CasebookApplication() {
    }

    @Override
    public byte[] handleRequest(IHttpContext httpContext) {
        String url = httpContext.getRequest().getUrl();
        RequestMethod requestMethod = RequestMethod.valueOf(httpContext.getRequest().getMethod());
        if (!this.routes.get(RequestMethod.GET).containsKey(url) &&
                !this.routes.get(RequestMethod.POST).containsKey(url)) {
            return new PageNotFoundHandler().buildResponse(httpContext.getRequest(), httpContext.getResponse()).getBytes();
        }

        return this.routes.get(requestMethod).get(url).apply(httpContext);
    }

    @Override
    public void establishResources() {
        HandlerLoader.loadApplicationHandlers();
        ApplicationRoutesLoader.loadApplicationRoutes(this.session, this.repository);
        this.routes = ApplicationRoutesLoader.getRoutes();
    }

    @Override
    public Map<RequestMethod, Map<String, Function<IHttpContext, byte[]>>> getRoutes() {
        return Collections.unmodifiableMap(this.routes);
    }

    @Override
    public IHttpSessionStorage getSession() {
        return this.session;
    }

    @Override
    public void setRepository(DbRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setSession(IHttpSessionStorage session) {
        this.session = session;
    }
}
