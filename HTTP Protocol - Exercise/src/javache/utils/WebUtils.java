package javache.utils;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public final class WebUtils {
    private WebUtils() {
    }

    public static String getFileExtension(String param) {
        int dotIndex = param.lastIndexOf(WebConstants.DOT);
        String fileExtension;

        try {
            fileExtension = param.substring(dotIndex, param.length()).substring(1);
        } catch (StringIndexOutOfBoundsException sioobe) {
            fileExtension = WebConstants.EMPTY;
        }

        return fileExtension;
    }
}
