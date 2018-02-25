package bindingModels;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public class UserRegisterBindingModel {
    private String username;
    private String gender;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}