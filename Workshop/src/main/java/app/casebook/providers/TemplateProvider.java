package main.java.app.casebook.providers;

import main.java.app.javache.io.InputStreamReader;

import java.io.FileInputStream;
import java.io.IOException;

import static main.java.app.casebook.utilities.ApplicationConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class TemplateProvider implements IProvider {
    public TemplateProvider() {
    }

    @Override
    public String provide(String resourceAbsolutePath) {
        String head, header, footer, view = null, bundle = null;

        try {
            bundle = InputStreamReader.readClientInputStream(new FileInputStream(BUNDLE_HTML_PATH));

            head = InputStreamReader.readClientInputStream(new FileInputStream(HEAD_HTML_PATH));
            header = InputStreamReader.readClientInputStream(new FileInputStream(HEADER_HTML_PATH));
            footer = InputStreamReader.readClientInputStream(new FileInputStream(FOOTER_HTML_PATH));

            bundle = bundle.replace(HEAD_TEMPLATE, head);
            bundle = bundle.replace(HEADER_TEMPLATE, header);
            bundle = bundle.replace(FOOTER_TEMPLATE, footer);

            view = InputStreamReader.readClientInputStream(new FileInputStream(resourceAbsolutePath));
            bundle = bundle.replace(VIEW_TEMPLATE, view);

            return bundle;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
