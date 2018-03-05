package bookhut.models.bindingModels;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class LoginBindingModel {
    private String username;
    private String password;

    public LoginBindingModel() {
    }

    public LoginBindingModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
