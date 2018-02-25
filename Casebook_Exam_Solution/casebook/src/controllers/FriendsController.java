package controllers;

import entities.User;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.*;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/25/2018.
 */
@Controller
public class FriendsController extends BaseController {

    public FriendsController() {
        super();
    }

    private String constructHtmlTableContent(User loggedUser) {
        StringBuilder builder = new StringBuilder();

        for (User friend : loggedUser.getFriends()) {
            builder.append("<tr class=\"spaceUnder\">")
                    .append("<td>" + friend.getUsername() + "</td>")
                    .append("<td><a href=\"/friends/remove/" + friend.getId() + "\" class=\"btn btn-danger\">Unfriend</a>")
                    .append("</td>")
                    .append("<tr>");
        }
        return builder.toString();
    }

    @GetMapping(route = FRIENDS_ROUTE)
    @PreAuthorize(isLoggedIn = true)
    public String listFriends(HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model, true);

        User loggedUser = this.getCurrentlyLoggedUserObject(request);

        String htmlContent = this.constructHtmlTableContent(loggedUser);

        model.addAttribute(USER_ID, this.getCurrentlyLoggedUserId(request));
        model.addAttribute(FRIENDS_TABLE, htmlContent);

        return TEMPLATE_FRIENDS;
    }

    @GetMapping(route = FRIENDS_ADD)
    @PreAuthorize(isLoggedIn = true)
    public String addFriend(@PathVariable String id, HttpSoletRequest request, Model model) {
        String currentLoggedInUserId = this.getCurrentlyLoggedUserId(request);

        User currentUser = this.userRepository.findById(currentLoggedInUserId);
        User friend = this.userRepository.findById(id);

        currentUser.addFriend(friend);
        friend.addFriend(currentUser);

        this.userRepository.updateUser(currentUser);
        this.userRepository.updateUser(friend);

        return REDIRECT_HOME;
    }

    @GetMapping(route = FRIENDS_REMOVE_ID)
    @PreAuthorize(isLoggedIn = true)
    public String removeFriend(@PathVariable String id, HttpSoletRequest request, Model model) {
        String currentLoggedInUserId = this.getCurrentlyLoggedUserId(request);

        User currentUser = this.userRepository.findById(currentLoggedInUserId);
        User friend = this.userRepository.findById(id);

        currentUser.removeFriend(this.userRepository.findById(id));
        friend.removeFriend(currentUser);

        this.userRepository.updateUser(currentUser);
        this.userRepository.updateUser(friend);

        return REDIRECT_HOME;
    }
}
