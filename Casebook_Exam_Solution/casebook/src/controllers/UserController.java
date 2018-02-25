package controllers;

import bindingModels.UserLoginBindingModel;
import bindingModels.UserRegisterBindingModel;
import entities.Gender;
import entities.User;
import factories.UserFactory;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.*;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/23/2018.
 */

@Controller
public class UserController extends BaseController {


    public UserController() {
        super();
    }

    @GetMapping(route = PROFILE_DETAILS)
    @PreAuthorize(isLoggedIn = true)
    public String profile(@PathVariable String id, HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model, true);

        User user = this.userRepository.findById(id);
        model.addAttribute(USERNAME, user.getUsername());
        model.addAttribute(GENDER, Gender.parseValue(user.getGender()));
        model.addAttribute(PICTURE, user.getGender().toString().toLowerCase());

        return TEMPLATE_PROFILE;
    }

    @GetMapping(route = REGISTER)
    @PreAuthorize
    public String register(HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model, false);

        return TEMPLATE_REGISTER;
    }

    @PostMapping(route = REGISTER)
    @PreAuthorize
    public String registerProcess(HttpSoletRequest request, UserRegisterBindingModel userRegisterBindingModel, Model model) {
        this.seedCommonAttributes(model, false);

        if (this.userRepository.findByName(userRegisterBindingModel.getUsername()) != null ||
                !super.validateGender(userRegisterBindingModel.getGender()) ||
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
        this.seedCommonAttributes(model, false);
        model.addAttribute(DISPLAY, STYLE_DISPLAY_NONE);

        return TEMPLATE_LOGIN;
    }

    @PostMapping(route = LOGIN)
    @PreAuthorize
    public String loginProcess(HttpSoletRequest request, UserLoginBindingModel userLoginBindingModel, Model model) {
        this.seedCommonAttributes(model, false);

        User user = this.userRepository.findByName(userLoginBindingModel.getUsername());
        if (user == null || !user.getPassword().equals(userLoginBindingModel.getPassword())) {
            model.addAttribute(ERROR, INCORRECT_USERNAME_OR_PASSWORD_MESSAGE);
            model.addAttribute(DISPLAY, STYLE_DISPLAY_BLOCK);
            model.addAttribute(TYPE, DANGER);

            return TEMPLATE_LOGIN;
        }

        request.getSession().addAttribute(USER_ID, user.getId());
        request.getSession().addAttribute(USERNAME, user.getUsername());

        return REDIRECT_HOME;
    }

    @GetMapping(route = LOGOUT)
    @PreAuthorize(isLoggedIn = true)
    public String logout(HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model, true);

        request.getSession().invalidate();

        return REDIRECT_INDEX;
    }
}