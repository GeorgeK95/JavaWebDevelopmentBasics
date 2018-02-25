package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by George-Lenovo on 02/23/2018.
 */

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany()
    @JoinTable(name = "friends",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "friendId")})
    private Set<User> friends;

    public User() {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("<div style=\"margin-top: 15px\" class=\"col-md-2 flex-column text-center\">")
                .append("<img src=\""
                        + Gender.getSimpleValue(this.gender) + ".png\" class=\"img-thumbnail\" width=\"200\" height=\"200\">")
                .append("<h5 class=\"text-center\"><a href=\"/profile/" + this.getId() + "\">"
                        + this.getUsername() + "</a></h5>")
                .append("<a href=\"/friends/add/" + this.getId() + "\" class=\"btn btn-info btn-block\">Add Friend</a>")
                .append("</div>");

        return builder.toString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void removeFriend(User user) {
        this.friends = this.friends.stream().filter(u -> !u.getUsername().equals(user.getUsername())).collect(Collectors.toSet());
    }

}