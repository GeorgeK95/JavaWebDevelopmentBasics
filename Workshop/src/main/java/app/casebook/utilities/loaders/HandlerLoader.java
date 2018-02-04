package main.java.app.casebook.utilities.loaders;

import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.annotations.Post;
import main.java.app.casebook.handlers.contracts.Handler;
import main.java.app.javache.enums.RequestMethod;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class HandlerLoader {
    private static Map<Class<? extends Handler>, Map<String, Method>> getRequestMethods;
    private static Map<Class<? extends Handler>, Map<String, Method>> postRequestMethods;

    public static void loadApplicationHandlers() {
        HandlerLoader.getRequestMethods = new HashMap<>();
        HandlerLoader.postRequestMethods = new HashMap<>();
        for (Class<? extends Handler> targetClass : HandlerClassesLoader.getFilteredHandlerClasses()) {
            HandlerLoader.fillMap(targetClass);
        }
    }

    private static void fillMap(Class<? extends Handler> targetClass) {
        List<Method> allGetMethods = Arrays.stream(targetClass.getMethods()).filter(m -> m.getAnnotations().length > 0)
                .collect(Collectors.toList());
        for (Method currentGetMethod : allGetMethods) {
            Map<String, Method> valueMap = null;
            if (currentGetMethod.getAnnotations()[0].annotationType().getSimpleName().toUpperCase().equals(RequestMethod.GET.name())) {
                valueMap = new HashMap<String, Method>() {{
                    put(currentGetMethod.getAnnotation(Get.class).route(),
                            currentGetMethod);
                    put(currentGetMethod.getAnnotation(Get.class).route(),
                            currentGetMethod);
                }};
                HandlerLoader.getRequestMethods.put(targetClass, valueMap);
            } else if (currentGetMethod.getAnnotations()[0].annotationType().getSimpleName().toUpperCase().equals(RequestMethod.POST.name())) {
                valueMap = new HashMap<String, Method>() {{
                    put(currentGetMethod.getAnnotation(Post.class).route(),
                            currentGetMethod);
                    put(currentGetMethod.getAnnotation(Post.class).route(),
                            currentGetMethod);
                }};
                HandlerLoader.postRequestMethods.put(targetClass, valueMap);
            }

        }
    }

    public static Map<Class<? extends Handler>, Map<String, Method>> getGetRequestMethods() {
        return Collections.unmodifiableMap(HandlerLoader.getRequestMethods);
    }

    public static Map<Class<? extends Handler>, Map<String, Method>> getPostRequestMethods() {
        return Collections.unmodifiableMap(HandlerLoader.postRequestMethods);
    }

    private HandlerLoader() {
    }
}
