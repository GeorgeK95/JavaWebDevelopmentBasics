package org.softuni.summer.core;

import org.softuni.broccolina.solet.*;
import org.softuni.javache.http.HttpStatus;
import org.softuni.summer.api.PreAuthorize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@WebSolet(route = "/*", loadOnStartUp = true)
public class DispatcherSolet extends BaseHttpSolet {

    private ControllerActionManager controllerActionManager;

    private TemplateEngine templateEngine;

    private ActionInvoker actionInvoker;

    public DispatcherSolet(SoletConfig soletConfig) {
        super(soletConfig);
        this.initThirdParties();
    }

    private void initThirdParties() {
        this.templateEngine = new TemplateEngine(
                this.getSoletConfig().getAttribute("application-path")
                        + File.separator
                        + "resources"
                        + File.separator
                        + "templates"
                        + File.separator);

        this.controllerActionManager = new ControllerActionManager(
                this.getSoletConfig().getAttribute("application-path")
                        + File.separator
                        + "classes"
                        + File.separator);

        this.actionInvoker = new ActionInvoker();
    }

    private String handleResult(String result, HttpSoletResponse response) {
        response.setStatusCode(HttpStatus.OK);

        if (this.templateEngine.isTemplate(result)) {
            response.addHeader("Content-Type", "text/html");

            try {
                result = this.templateEngine.loadTemplate(result.split("\\:")[1]);
            } catch (IOException e) {
                response.setStatusCode(HttpStatus.NOT_FOUND);
                result = "<h1>Template not found!</h1>";
            }
        } else if (result.startsWith("redirect:")) {
            String url = result.split("\\:")[1];

            response.setStatusCode(HttpStatus.SEE_OTHER);

            response.addHeader("Location", url);
        } else {
            response.addHeader("Content-Type", "text/plain");
        }

        return result;
    }

    private boolean isLoggedIn(HttpSoletRequest request) {
        return request.getSession().getAttributes().containsKey("user-id");
    }

    @Override
    public void init() {
        super.init();

        this.controllerActionManager.loadControllers();
    }

    @Override
    public void service(HttpSoletRequest request, HttpSoletResponse response) {
        String requestMethod = request.getMethod();
        String requestUrl = request.getRequestUrl();

        ControllerActionPair cap = this
                .controllerActionManager
                .getControllerActionPair(requestMethod, requestUrl);

        if (cap != null) {
            String result = null;

            try {
                Object preAuthorize = Arrays.stream(cap.getAction().getAnnotations())
                        .filter(x -> x.annotationType()
                                .getSimpleName()
                                .equals(PreAuthorize.class.getSimpleName()))
                        .findFirst()
                        .orElse(null);

                if (preAuthorize != null) {

                    boolean annotationIsLoggedIn = (boolean) Arrays.stream(preAuthorize
                            .getClass()
                            .getDeclaredMethods())
                            .filter(x -> x.getName().equals("isLoggedIn"))
                            .findFirst()
                            .orElse(null)
                            .invoke(preAuthorize);

                    if (this.isLoggedIn(request) != annotationIsLoggedIn) {
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);

                        response.addHeader("Content-Type", "text/plain");

                        result = "Permission denied.";

                        return;
                    }
                }

                result = this.actionInvoker.invokeAction(
                        cap
                        , this.templateEngine.getNewModel()
                        , request
                        , response);

                result = this.handleResult(result, response);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                response.addHeader("Content-Type", "text/plain");

                StringWriter errorWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(errorWriter));
                result = errorWriter.toString();
            } finally {
                response.setContent(result.getBytes());
            }
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND);
            response.addHeader("Content-Type", "text/plain");
            response.setContent("No such functionality found...".getBytes());
        }
    }
}
