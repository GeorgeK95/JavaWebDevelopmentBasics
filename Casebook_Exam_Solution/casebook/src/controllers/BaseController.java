package controllers;

import entities.User;
import factories.UserRepositoryFactory;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.Model;
import repositories.UserRepository;
import utils.FileReader;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/25/2018.
 */
public abstract class BaseController {
    protected UserRepository userRepository;

    protected BaseController() {
        this.userRepository = UserRepositoryFactory.create();
    }

    protected void seedCommonAttributes(Model model, boolean isLogged) {
        model.addAttribute(HEAD_STRING, this.readHtmlResource(HEAD_ABSOLUTE_PARH));
        if (isLogged)
            model.addAttribute(LOGGED_NAVIGATION_STRING, this.readHtmlResource(LOGGED_NAVIGATION_ABSOLUTE_PARH));
        else
            model.addAttribute(GUEST_NAVIGATION_STRING, this.readHtmlResource(GUEST_NAVIGATION_ABSOLUTE_PARH));

        model.addAttribute(FOOTER_STRING, this.readHtmlResource(FOOTER_ABSOLUTE_PARH));
    }

    protected User getCurrentlyLoggedUserObject(HttpSoletRequest request) {
        String currentLoggedInUserId = (String) request.getSession().getAttributes().get(USER_ID);
        return userRepository.findById(currentLoggedInUserId);
    }

    protected String getCurrentlyLoggedUserId(HttpSoletRequest request) {
        return (String) request.getSession().getAttributes().get(USER_ID);
    }

    protected boolean validateGender(String gender) {
        return gender.equalsIgnoreCase(MALE) ||
                gender.equalsIgnoreCase(FEMALE);
    }

    protected String readHtmlResource(String path) {
        return FileReader.readHTMLFile(path);
    }
}
