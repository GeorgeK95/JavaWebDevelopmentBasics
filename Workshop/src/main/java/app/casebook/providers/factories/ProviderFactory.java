package main.java.app.casebook.providers.factories;

import main.java.app.casebook.providers.IProvider;
import main.java.app.casebook.providers.TemplateProvider;
import main.java.app.casebook.providers.ViewProvider;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ProviderFactory {

    public static IProvider createTemplateProvider() {
        return new TemplateProvider();
    }

    public static IProvider createViewProvider() {
        return new ViewProvider();
    }
}
