package main.java.app.casebook;

import main.java.app.casebook.handlers.contracts.Handler;
import main.java.app.casebook.handlers.fixed.PageNotFoundHandler;
import main.java.app.casebook.handlers.loader.HandlerLoader;
import main.java.app.database.annotations.Repository;
import main.java.app.database.repositories.DbRepository;
import main.java.app.javache.Application;
import main.java.app.javache.annotations.HttpSession;
import main.java.app.javache.enums.RequestMethod;
import main.java.app.javache.http.contracts.IHttpContext;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.test.contracts.IHttpSessionStorage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static main.java.app.casebook.utilities.ApplicationConstants.BUILD_RESPONSE;
import static main.java.app.casebook.utilities.ApplicationConstants.BUILD_RESPONSE_PROCESS;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class CasebookApplication implements Application {
    private Map<RequestMethod, Map<String, Function<IHttpContext, byte[]>>> routes;
    private IHttpSessionStorage session;
    private DbRepository repository;

    public CasebookApplication() {
        this.loadApplicationData();
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

    private void loadApplicationData() {
        this.routes = new HashMap<>();
        this.routes.put(RequestMethod.GET, new HashMap<>());
        this.routes.put(RequestMethod.POST, new HashMap<>());

        HandlerLoader.loadHandlers();
        this.initRoutes(HandlerLoader.getGetRequestMethods(), RequestMethod.GET);
        this.initRoutes(HandlerLoader.getPostRequestMethods(), RequestMethod.POST);
    }

    private void initRoutes(Map<Class<? extends Handler>, Map<String, Method>> targetMethods, RequestMethod methodType) {
        for (Map.Entry<Class<? extends Handler>, Map<String, Method>> classRouteMethod : targetMethods.entrySet()) {
            for (Map.Entry<String, Method> routeMethodPair : classRouteMethod.getValue().entrySet()) {
                Function function = this.initFunction(classRouteMethod.getKey().getName(),
                        methodType == RequestMethod.GET ? RequestMethod.GET : RequestMethod.POST);
                this.routes.get(methodType).put(routeMethodPair.getKey(), function);
            }
        }
    }

    private Function initFunction(String className, RequestMethod requestMethod) {
        return (Function<IHttpContext, byte[]>) p -> {
            try {
                Class<? extends Handler> handlerClass = (Class<? extends Handler>) Class.forName(className);
                Handler handler = (Handler) handlerClass.getConstructors()[0].newInstance();
                this.injectDependencies(handlerClass, handler);
                String methodName = this.generateMethodName(requestMethod);
                IHttpResponse invoked = this.invokeMethod(requestMethod, handler, p, handlerClass,
                        methodName);
                return invoked.getBytes();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private void injectDependencies(Class<? extends Handler> handlerClass, Handler handler) throws IllegalAccessException {
        this.injectField(handler, this.getField(handlerClass, Repository.class), this.repository);
        this.injectField(handler, this.getField(handlerClass, HttpSession.class), this.session);
    }

    private Field getField(Class<? extends Handler> handlerClass, Class<? extends Annotation> repositoryClass) {
        for (Field field : handlerClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(repositoryClass)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    private void injectField(Handler handler, Field repoField, Object obj) throws IllegalAccessException {
        if (repoField != null) repoField.set(handler, obj);
    }

    private IHttpResponse invokeMethod(RequestMethod requestMethod, Handler handler, IHttpContext p,
                                       Class<? extends Handler> handlerClass, String methodName) {
        IHttpResponse invoked = null;
        Method buildResponseMethod = null;
        try {
            if (requestMethod == RequestMethod.GET) {
                buildResponseMethod = handlerClass.getMethod(methodName, IHttpRequest.class, IHttpResponse.class);
                invoked = (IHttpResponse) buildResponseMethod.invoke(handler, p.getRequest(), p.getResponse());
            } else {
                buildResponseMethod = handlerClass.getMethod(methodName, IHttpRequest.class, IHttpResponse.class);
                invoked = (IHttpResponse) buildResponseMethod.invoke(handler, p.getRequest(), p.getResponse());
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return invoked;
    }

    private String generateMethodName(RequestMethod requestMethod) {
        String methodName;
        if (requestMethod == RequestMethod.GET) {
            methodName = BUILD_RESPONSE;
        } else {
            methodName = BUILD_RESPONSE_PROCESS;
        }
        return methodName;
    }
}
