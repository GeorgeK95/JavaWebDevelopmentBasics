package javache.utils;

import javache.io.InputStreamReader;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class ViewGenerator {
    public static String generateProfileView(String absolutePath, String... params) {
        String htmlContent;
        try {
            htmlContent = InputStreamReader.readClientInputStream(new FileInputStream(absolutePath));
            return String.format(htmlContent, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateHomeView(String absolutePath, String... params) {
        String htmlContent;
        try {
            htmlContent = InputStreamReader.readClientInputStream(new FileInputStream(absolutePath));
            String otherUsers = ViewGenerator.formatHomeView(params);
            return String.format(htmlContent, otherUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String formatHomeView(String[] emails) {
        StringBuilder builder = new StringBuilder();
        for (String email : emails) {
            builder.append("<p>").append(email).append("</p>");
        }
        return builder.toString();
    }
}
