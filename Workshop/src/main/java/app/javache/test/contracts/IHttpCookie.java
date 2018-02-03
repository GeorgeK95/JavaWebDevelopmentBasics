package main.java.app.javache.test.contracts;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpCookie {
    /*static IHttpCookie getUserCookie(List<IHttpCookie> iHttpCookies, String criteria) {
        for (IHttpCookie iHttpCookie : iHttpCookies) {
            for (Map.Entry<String, String> httpCookie : iHttpCookie.getCookies().entrySet()) {
                if (httpCookie.getKey().equals(criteria)) {
                    return iHttpCookie;
                }
            }
        }
        return null;
    }*/

    String getCookieName();

    String getCookieValue();

    void writeToDb(String cookieName, String cookieValue);

   /* Map<String, String> getCookies();

    void addSession(String key, String value);

    boolean persistCookies(String dbAbsolutePath);

    void clearCookies();

    String cookieValueByKey(String cookieName);*/

}
