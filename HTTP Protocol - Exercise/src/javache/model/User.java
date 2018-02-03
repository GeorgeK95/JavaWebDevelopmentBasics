package javache.model;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class User {
    private String id;
    private String email;
    private String password;
    private String confirm;

    public User(String id,String username, String password) {
        this.id = id;
        this.email = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
