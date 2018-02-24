package controllers;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.Controller;
import org.softuni.summer.api.GetMapping;
import org.softuni.summer.api.Model;
import utils.Constants;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/23/2018.
 */

@Controller
public class HomeController {


    private boolean isLoggedIn(HttpSoletRequest request) {
        return request.getSession().getAttributes().containsKey(USER_ID);
    }

    @GetMapping(route = HOME)
    public String index(HttpSoletRequest request, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        if (isLoggedIn(request)) {
            model.addAttribute(LOGGED_NAVIGATION_STRING, Constants.LOGGED_NAVIGATION);
            model.addAttribute(USERNAME, request.getSession().getAttributes().get(USERNAME));
            return TEMPLATE_LOGGED_IN;
        }
        model.addAttribute(GUEST_NAVIGATION_STRING, Constants.GUEST_NAVIGATION);
        return TEMPLATE_INDEX;
    }
}
