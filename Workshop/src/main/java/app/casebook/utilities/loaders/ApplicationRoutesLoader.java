package main.java.app.casebook.utilities.loaders;

import main.java.app.casebook.handlers.contracts.Handler;
import main.java.app.database.annotations.Repository;
import main.java.app.database.repositories.DbRepository;
import main.java.app.javache.annotations.HttpSession;
import main.java.app.javache.enums.RequestMethod;
import main.java.app.javache.http.contracts.IHttpContext;
import main.java.app.javache.http.contracts.IHttpRequest;
import main.java.app.javache.http.contracts.IHttpResponse;
import main.java.app.javache.http.contracts.IHttpSessionStorage;

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
public final class ApplicationRoutesLoader {

    private static Map<RequestMethod, Map<String, Function<IHttpContext, byte[]>>> routes;
    private static IHttpSessionStorage session;
    private static DbRepository repository;

    private ApplicationRoutesLoader() {
    }

    public static void loadApplicationRoutes(IHttpSessionStorage session, DbRepository repository) {
        ApplicationRoutesLoader.routes = new HashMap<>();
        ApplicationRoutesLoader.routes.put(RequestMethod.GET, new HashMap<>());
        ApplicationRoutesLoader.routes.put(RequestMethod.POST, new HashMap<>());
        ApplicationRoutesLoader.session = session;
        ApplicationRoutesLoader.repository = repository;
        ApplicationRoutesLoader.initRoutes(HandlerLoader.getGetRequestMethods(), RequestMethod.GET);
        ApplicationRoutesLoader.initRoutes(HandlerLoader.getPostRequestMethods(), RequestMethod.POST);
    }

    private static void initRoutes(Map<Class<? extends Handler>, Map<String, Method>> targetMethods, RequestMethod methodType) {
        for (Map.Entry<Class<? extends Handler>, Map<String, Method>> classRouteMethod : targetMethods.entrySet()) {
            for (Map.Entry<String, Method> routeMethodPair : classRouteMethod.getValue().entrySet()) {
                Function function = initFunction(classRouteMethod.getKey().getName(),
                        methodType == RequestMethod.GET ? RequestMethod.GET : RequestMethod.POST);
                routes.get(methodType).put(routeMethodPair.getKey(), function);
            }
        }
    }

    private static Function initFunction(String className, RequestMethod requestMethod) {
        return (Function<IHttpContext, byte[]>) p -> {
            try {
                Class<? extends Handler> handlerClass = (Class<? extends Handler>) Class.forName(className);
                Handler handler = (Handler) handlerClass.getConstructors()[0].newInstance();
                ApplicationRoutesLoader.injectDependencies(handlerClass, handler);
                String methodName = ApplicationRoutesLoader.generateMethodName(requestMethod);
                IHttpResponse invoked = ApplicationRoutesLoader.invokeMethod(requestMethod, handler, p, handlerClass,
                        methodName);
                return invoked.getBytes();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private static void injectDependencies(Class<? extends Handler> handlerClass, Handler handler) throws IllegalAccessException {
        ApplicationRoutesLoader.injectField(handler, ApplicationRoutesLoader.getField(handlerClass, Repository.class), ApplicationRoutesLoader.repository);
        ApplicationRoutesLoader.injectField(handler, ApplicationRoutesLoader.getField(handlerClass, HttpSession.class), ApplicationRoutesLoader.session);
    }

    private static Field getField(Class<? extends Handler> handlerClass, Class<? extends Annotation> repositoryClass) {
        for (Field field : handlerClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(repositoryClass)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    private static void injectField(Handler handler, Field field, Object obj) throws IllegalAccessException {
        if (field != null) field.set(handler, obj);
    }

    private static IHttpResponse invokeMethod(RequestMethod requestMethod, Handler handler, IHttpContext p,
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

    private static String generateMethodName(RequestMethod requestMethod) {
        String methodName;
        if (requestMethod == RequestMethod.GET) {
            methodName = BUILD_RESPONSE;
        } else {
            methodName = BUILD_RESPONSE_PROCESS;
        }
        return methodName;
    }

    public static Map<RequestMethod, Map<String, Function<IHttpContext, byte[]>>> getRoutes() {
        return Collections.unmodifiableMap(routes);
    }
}
