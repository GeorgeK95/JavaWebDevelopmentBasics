package controllers;

import bindingModels.UserLoginBindingModel;
import bindingModels.UserRegisterBindingModel;
import entities.User;
import factories.UserFactory;
import factories.UserRepositoryFactory;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.*;
import repositories.UserRepository;
import utils.Constants;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/23/2018.
 */

@Controller
public class UserController {


    private UserRepository userRepository;

    public UserController() {
        this.userRepository = UserRepositoryFactory.create();
    }

    @GetMapping(route = REGISTER)
    @PreAuthorize
    public String register(HttpSoletRequest request, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        model.addAttribute(GUEST_NAVIGATION_STRING, Constants.GUEST_NAVIGATION);
        return TEMPLATE_REGISTER;
    }

    @PostMapping(route = REGISTER)
    @PreAuthorize
    public String registerProcess(HttpSoletRequest request, UserRegisterBindingModel userRegisterBindingModel, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        model.addAttribute(GUEST_NAVIGATION_STRING, Constants.GUEST_NAVIGATION);

        if (this.userRepository.findByName(userRegisterBindingModel.getUsername()) != null ||
                this.userRepository.findByEmail(userRegisterBindingModel.getEmail()) != null ||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return REDIRECT_REGISTER;
        }

        User user = UserFactory.create(userRegisterBindingModel);
        this.userRepository.create(user);

        return REDIRECT_LOGIN;
    }

    @GetMapping(route = LOGIN)
    @PreAuthorize
    public String login(HttpSoletRequest request, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        model.addAttribute(GUEST_NAVIGATION_STRING, Constants.GUEST_NAVIGATION);
        return TEMPLATE_LOGIN;
    }

    @PostMapping(route = LOGIN)
    @PreAuthorize
    public String loginProcess(HttpSoletRequest request, UserLoginBindingModel userLoginBindingModel, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        User user = (User) this.userRepository.findByName(userLoginBindingModel.getUsername());

        if (user == null || !user.getPassword().equals(userLoginBindingModel.getPassword())) {
            return REDIRECT_LOGIN;
        }

        request.getSession().addAttribute(USER_ID, user.getId());
        request.getSession().addAttribute(USERNAME, user.getUsername());

        return REDIRECT_HOME;
    }

    @GetMapping(route = LOGOUT)
    @PreAuthorize(isLoggedIn = true)
    public String logout(HttpSoletRequest request, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        model.addAttribute(GUEST_NAVIGATION_STRING, Constants.GUEST_NAVIGATION);
        request.getSession().invalidate();
        return REDIRECT_INDEX;
    }
}