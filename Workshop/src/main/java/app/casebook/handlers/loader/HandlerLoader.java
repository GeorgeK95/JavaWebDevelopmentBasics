package main.java.app.casebook.handlers.loader;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.annotations.Get;
import main.java.app.casebook.annotations.Post;
import main.java.app.casebook.handlers.contracts.Handler;
import main.java.app.javache.enums.RequestMethod;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static main.java.app.javache.utils.ServerConstants.HANDLERS_PACKAGE_PATH;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class HandlerLoader {
    private static Map<Class<? extends Handler>, Map<String, Method>> getRequestMethods;
    private static Map<Class<? extends Handler>, Map<String, Method>> postRequestMethods;

    public static void loadHandlers() {
        HandlerLoader.getRequestMethods = new HashMap<>();
        HandlerLoader.postRequestMethods = new HashMap<>();
        for (Class<? extends Handler> targetClass : HandlerLoader.getHandlerClasses()) {
            if (targetClass.isAnnotationPresent(ApplicationRequestHandler.class)) {
                HandlerLoader.fillMap(targetClass);
//                HandlerLoader.injectRepository(targetClass, userRepository);
            }
        }
    }

    /*private static void injectRepository(Class<? extends Handler> targetClass, IUserRepository<User> userRepository) {
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Repository.class)) {
                field.setAccessible(true);
                try {
                    Handler handler = targetClass.newInstance();
                    field.set(handler, userRepository);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(String.format("Failed to inject user repository in %shandler.", targetClass.getSimpleName()));
                }
            }
        }
    }*/

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

    private static Set<Class<? extends Handler>> getHandlerClasses() {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(HANDLERS_PACKAGE_PATH))));

        return reflections.getSubTypesOf(Handler.class);
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
