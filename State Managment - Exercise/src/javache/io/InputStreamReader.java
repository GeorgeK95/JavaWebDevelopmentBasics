package javache.io;

import javache.utils.WebUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static javache.utils.WebConstants.*;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class InputStreamReader {
    private InputStreamReader() {
    }

    public static String readClientInputStream(InputStream requestStream) throws IOException {
        BufferedReader buffer = new BufferedReader(new java.io.InputStreamReader(requestStream));
        StringBuilder builder = new StringBuilder();
        while (buffer.ready()) {
            builder.append((char) buffer.read());
        }
        return builder.toString();
    }

    public static byte[] readResourceAndGetHtmlContent(String targetResource) {
        String htmlContent = EMPTY;
        String path = null;
        try {
            String fileExtension = WebUtils.getFileExtension(targetResource);

            switch (fileExtension) {
                case EMPTY:
                    targetResource = targetResource.concat(HTML_EXTENSION);
                case HTML_CONTENT_TYPE_STRING:
                    path = PROJECT_DIRECTORY.concat(RESOURCE_FOLDER).concat(ASSETS).concat(targetResource);
                    break;
                case PNG_EXTENSION_STRING:
                    path = PROJECT_DIRECTORY.concat(RESOURCE_FOLDER).concat(ASSETS + IMAGE_STRING + "\\").concat(targetResource);
                    return Files.readAllBytes(Paths.get(path));
                case CSS_CONTENT_TYPE_STRING:
                    path = PROJECT_DIRECTORY.concat(RESOURCE_FOLDER).concat(ASSETS).concat(targetResource);
                    return Files.readAllBytes(Paths.get(path));
            }

            File f = new File(path);
            if (f.exists()) {
                //200 404 ..
                htmlContent = InputStreamReader.readClientInputStream(new FileInputStream(path));
            } else {
                if (targetResource.equals(HOME + HTML_EXTENSION)) {
                    path = PROJECT_DIRECTORY.concat(RESOURCE_FOLDER).concat(PAGES).concat(HOME + "\\").concat(targetResource);
                } else if (targetResource.equals(PROFILE + HTML_EXTENSION)) {
                    path = PROJECT_DIRECTORY.concat(RESOURCE_FOLDER).concat(PAGES).concat(PROFILE + "\\").concat(targetResource);
                }

                f = new File(path);
                if (f.exists()) {
                    //failed to find static page, but found dynamic one
                    htmlContent = InputStreamReader.readClientInputStream(new FileInputStream(path));
                } else {
                    String page404Content = InputStreamReader.readClientInputStream(new FileInputStream(ERROR_404_ABSOLUTE_PATH));
                    return page404Content.getBytes();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlContent.getBytes();
    }
}
