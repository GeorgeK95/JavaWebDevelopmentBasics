package bookhut.entities;

import bookhut.utils.IdGenerator;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class User {
    private int id;
    private String userName;
    private String password;

    public User() {
        this.setId();
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void setId() {
        this.id = IdGenerator.generateId();
    }
}
