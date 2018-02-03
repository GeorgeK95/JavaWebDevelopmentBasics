package javache.utils;

import javache.io.InputStreamReader;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class DynamicPageLoader {
    public static String formatDynamicPage(String absolutePath, String... params) {
        String htmlContent;
        try {
            htmlContent = InputStreamReader.readClientInputStream(new FileInputStream(absolutePath));
            return String.format(htmlContent, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
