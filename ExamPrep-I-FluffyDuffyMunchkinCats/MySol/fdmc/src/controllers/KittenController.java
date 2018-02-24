package controllers;

import bindingModels.KittenAddBindingModel;
import entities.Kitten;
import factories.KittenFactory;
import factories.KittenRepositoryFactory;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.*;
import repositories.KittenRepository;
import utils.Constants;

import static utils.Constants.*;


/**
 * Created by George-Lenovo on 02/23/2018.
 */

@Controller
public class KittenController {


    private KittenRepository kittensRepository;

    public KittenController() {
        this.kittensRepository = KittenRepositoryFactory.create();
    }

    @GetMapping(route = KITTENS_ALL)
    @PreAuthorize(isLoggedIn = true)
    public String listKittens(HttpSoletRequest request, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);

        Kitten[] kittenList = this.kittensRepository.findAll();

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < kittenList.length; i++) {
            Kitten currentKitten = kittenList[i];

            if (i == 0) {
                builder.append("<div class=\"row\">").append(currentKitten.toString());
            } else if (i % 3 == 0) {
                builder.append("</div>").append("<div class=\"row\">").append(currentKitten.toString());
            } else {
                builder.append(currentKitten.toString());
            }
        }

        if (kittenList.length % 3 != 0) {
            builder.append("</div>");
        }

        model.addAttribute(ALL_KITTENS, builder.toString());
        model.addAttribute(LOGGED_NAVIGATION_STRING, Constants.LOGGED_NAVIGATION);

        return TEMPLATE_ALL_KITTENS;
    }

    @GetMapping(route = KITTENS_ADD)
    @PreAuthorize(isLoggedIn = true)
    public String addKitten(HttpSoletRequest request, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        model.addAttribute(LOGGED_NAVIGATION_STRING, Constants.LOGGED_NAVIGATION);
        return TEMPLATE_ADD_KITTEN;
    }

    @PostMapping(route = KITTENS_ADD)
    @PreAuthorize(isLoggedIn = true)
    public String addKittenProcess(HttpSoletRequest request, KittenAddBindingModel bindingModel, Model model) {
        model.addAttribute(HEAD_STRING, Constants.HEAD);
        model.addAttribute(FOOTER_STRING, Constants.FOOTER);
        Kitten kitten = KittenFactory.create(bindingModel);

        this.kittensRepository.create(kitten);

        model.addAttribute(LOGGED_NAVIGATION_STRING, Constants.LOGGED_NAVIGATION);
        return TEMPLATE_ADD_KITTEN;
    }
}
