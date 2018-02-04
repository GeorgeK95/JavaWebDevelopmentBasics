package main.java.app.casebook.utilities.loaders;

import main.java.app.casebook.annotations.ApplicationRequestHandler;
import main.java.app.casebook.handlers.contracts.Handler;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static main.java.app.javache.utils.ServerConstants.HANDLERS_PACKAGE_PATH;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class HandlerClassesLoader {
    public static Set<Class<? extends Handler>> getHandlerClasses() {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(HANDLERS_PACKAGE_PATH))));

        return reflections.getSubTypesOf(Handler.class);
    }

    public static Set<Class<? extends Handler>> getFilteredHandlerClasses() {
        Set<Class<? extends Handler>> collection = new HashSet<>();
        for (Class<? extends Handler> targetClass : HandlerClassesLoader.getHandlerClasses()) {
            if (targetClass.isAnnotationPresent(ApplicationRequestHandler.class)) {
                collection.add(targetClass);
            }
        }
        return collection;
    }

    private HandlerClassesLoader() {
    }
}
