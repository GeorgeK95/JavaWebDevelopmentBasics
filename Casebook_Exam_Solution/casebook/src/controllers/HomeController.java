package controllers;

import entities.User;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.Controller;
import org.softuni.summer.api.GetMapping;
import org.softuni.summer.api.Model;
import org.softuni.summer.api.PreAuthorize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
@Controller
public class HomeController extends BaseController {
    public HomeController() {
        super();
    }

    private User[] getCurrentUserNonFriends(String currentLoggedInUserId, HttpSoletRequest request) {
        User currentLoggedUser = this.getCurrentlyLoggedUserObject(request);

        Set<String> friends = currentLoggedUser.getFriends().stream().map(User::getUsername).collect(Collectors.toSet());
        List<User> allUsers = Arrays.asList(this.userRepository.findAll(currentLoggedInUserId));
        List<User> nonFriends = new ArrayList<>();

        for (User user : allUsers) {
            if (!friends.contains(user.getUsername())) {
                nonFriends.add(user);
            }
        }

        return nonFriends.toArray(new User[nonFriends.size()]);
    }

    private String constructHtmlContent(User[] allUsers) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < allUsers.length; i++) {
            User currentUser = allUsers[i];

            if (i == 0) {
                builder
                        .append("<div class=\"row mb-8 d-flex justify-content-around\">")
                        .append(currentUser.toString());
            } else if (i % 4 == 0) {
                builder
                        .append("</div>")
                        .append("<div class=\"row mb-8 d-flex justify-content-around\">")
                        .append(currentUser.toString());
            } else {
                builder.append(currentUser.toString());
            }
        }

        if (allUsers.length > 0) builder.append("</div>");
        return builder.toString();
    }

    @GetMapping(route = INDEX)
    @PreAuthorize
    public String index(Model model) {
        this.seedCommonAttributes(model, false);

        return TEMPLATE_INDEX;
    }

    @GetMapping(route = HOME)
    @PreAuthorize(isLoggedIn = true)
    public String home(HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model, true);

        String currentLoggedInUserId = (String) request.getSession().getAttributes().get(USER_ID);
        User[] allUsers = this.getCurrentUserNonFriends(currentLoggedInUserId, request);

        String htmlContent = this.constructHtmlContent(allUsers);

        model.addAttribute(ALL_USERS, htmlContent);
        model.addAttribute(USERNAME, request.getSession().getAttributes().get(USERNAME));
        model.addAttribute(CURRENT_USER_ID, request.getSession().getAttributes().get(USER_ID));

        return TEMPLATE_HOME;
    }

}
