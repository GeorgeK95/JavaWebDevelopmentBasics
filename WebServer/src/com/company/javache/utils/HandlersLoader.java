package com.company.javache.utils;

import com.company.javache.lib.handler.IRequestHandler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.company.javache.utils.ServerConstants.CLASS_TYPE;
import static com.company.javache.utils.ServerConstants.JAR_TYPE;
import static com.company.javache.utils.ServerConstants.STATIC_HANDLES_FOLDER;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class HandlersLoader {

    private static String ROOT_PATH;

   /*  private static Iterable<IRequestHandler> loadRequestHandlers(String path, Collection<IRequestHandler> handlers) {
        String handlersPath = rootPath + staticHandlesFolder;
        File targetFolder = new File(path);
        for (File file : targetFolder.listFiles()) {
            if (file.isDirectory()) {
                loadRequestHandlers(file.getPath(), handlers);
            } else {
                handlers = HandlersLoader.loadRequestHandlersFromFile(file, handlersPath, handlers);
            }
        }
        return handlers;
    }


   public static Collection<IRequestHandler> loadRequestHandlersFromFile(File file, String handlersPath,
                                                                          Collection<IRequestHandler> handlers) {
        if (file.getPath().endsWith(CLASS_TYPE)) {
            String absPath = file.getPath();
            String classAbsPath = absPath.substring(0, absPath.lastIndexOf(DOT))
                    .replace(handlersPath + File.separator, "");

            String className = classAbsPath.replace(File.separatorChar, DOT);
            ClassLoader urlClassLoader = null;
            try {
                urlClassLoader = new URLClassLoader(new URL[]{
                        new File(handlersPath)
                                .toURI()
                                .toURL()
                });
            } catch (MalformedURLException mue) {
                urlClassLoader = ClassLoader.getSystemClassLoader();
                mue.printStackTrace();
            }

            try {
                Class clazz = urlClassLoader.loadClass(className);
                if (IRequestHandler.class.isAssignableFrom(clazz)) {
                    IRequestHandler requestHandler = (IRequestHandler) clazz.getDeclaredConstructor(String.class)
                            .newInstance(rootPath);
                    handlers.add(requestHandler);
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
        return handlers;
    }

    public static Iterable<IRequestHandler> loadRequestHandlers(String rootPath) {
        HandlersLoader.rootPath = rootPath;
        return HandlersLoader.loadRequestHandlers(HandlersLoader.rootPath + File.separator + staticHandlesFolder, new ArrayList<>());
    }*/

    private HandlersLoader() {
    }

    public static Map<String, IRequestHandler> loadRequestHandlers(String rootPath) {
        HandlersLoader.ROOT_PATH = rootPath;
        return HandlersLoader.loadRequestHandlers(HandlersLoader.ROOT_PATH + File.separator + STATIC_HANDLES_FOLDER,
                new LinkedHashMap<>());
    }

    private static Map<String, IRequestHandler> loadRequestHandlers(String path, Map<String, IRequestHandler> handlers) {
        File targetFolder = new File(path);
        for (File file : targetFolder.listFiles()) {
            if (file.isDirectory()) {
                loadRequestHandlers(file.getPath(), handlers);
            } else {
                try {
                    handlers = HandlersLoader.loadRequestHandlersFromFile(file, handlers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return handlers;
    }

    public static Map<String, IRequestHandler> loadRequestHandlersFromFile(File file, Map<String, IRequestHandler> handlers) {
        try {
            if (file.getName().endsWith(JAR_TYPE)) {
                JarFile jarFile = new JarFile(file.getCanonicalPath());
                Enumeration<JarEntry> e = jarFile.entries();

                URL[] urls = {new URL("jar:file:" + file.getCanonicalPath() + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if (je.isDirectory() || !je.getName().endsWith(CLASS_TYPE)) {
                        continue;
                    }
                    // -6 because of .class
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class clazz = cl.loadClass(className);
                    if (IRequestHandler.class.isAssignableFrom(clazz)) {
                        IRequestHandler requestHandler = (IRequestHandler) clazz.getDeclaredConstructor(String.class)
                                .newInstance(ROOT_PATH);
                        handlers.put(requestHandler.getClass().getSimpleName(), requestHandler);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            /*String absPath = file.getPath();
            String classAbsPath = absPath.substring(0, absPath.lastIndexOf(DOT))
                    .replace(handlersPath + File.separator, "");

            String className = classAbsPath.replace(File.separatorChar, DOT);
            ClassLoader urlClassLoader = null;
            try {
                urlClassLoader = new URLClassLoader(new URL[]{
                        new File(handlersPath)
                                .toURI()
                                .toURL()
                });
            } catch (MalformedURLException mue) {
                urlClassLoader = ClassLoader.getSystemClassLoader();
                mue.printStackTrace();
            }

            try {
                Class clazz = urlClassLoader.loadClass(className);
                if (IRequestHandler.class.isAssignableFrom(clazz)) {
                    IRequestHandler requestHandler = (IRequestHandler) clazz.getDeclaredConstructor(String.class)
                            .newInstance(ROOT_PATH);
                    handlers.put(requestHandler.getClass().getSimpleName(), requestHandler);
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }*/

        return handlers;
    }


}
