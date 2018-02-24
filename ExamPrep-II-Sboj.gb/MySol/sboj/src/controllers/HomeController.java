package controllers;

import entities.JobApplication;
import factories.JobRepositoryFactory;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.Controller;
import org.softuni.summer.api.GetMapping;
import org.softuni.summer.api.Model;
import org.softuni.summer.api.PreAuthorize;
import repositories.Repository;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
@Controller
public class HomeController {

    private Repository<JobApplication> jobRepository;

    public HomeController() {
        this.jobRepository = JobRepositoryFactory.create();
    }

    private void seedCommonAttributes(Model model, String naviString, String navi) {
        model.addAttribute(HEAD_STRING, HEAD);
        model.addAttribute(naviString, navi);
        model.addAttribute(FOOTER_STRING, FOOTER);
    }

    @GetMapping(route = INDEX)
    @PreAuthorize
    public String index(Model model) {
        this.seedCommonAttributes(model, GUEST_NAVIGATION_STRING, GUEST_NAVIGATION);

        return TEMPLATE_INDEX;
    }

    @GetMapping(route = HOME)
    @PreAuthorize(isLoggedIn = true)
    public String home(HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model, LOGGED_NAVIGATION_STRING, LOGGED_NAVIGATION);

        model.addAttribute(USERNAME, request.getSession().getAttributes().get(USERNAME));

        StringBuilder builder = new StringBuilder();
        JobApplication[] allJobs = this.jobRepository.findAll();

        for (int i = 0; i < allJobs.length; i++) {
            JobApplication currentJobApplication = allJobs[i];
            System.out.println("vuv cikula:" + currentJobApplication.getId());
            if (i == 0) {
                builder
                        .append("<div class=\"row mb-4 d-flex justify-content-around\">")
                        .append(currentJobApplication.toString());
            } else if (i % 3 == 0) {
                builder
                        .append("</div>")
                        .append("<div class=\"row mb-4 d-flex justify-content-around\">")
                        .append(currentJobApplication.toString());
            } else {
                builder.append(currentJobApplication.toString());
            }
        }

        if (allJobs.length > 0) builder.append("</div>");

        model.addAttribute(JOB_APPLICATIONS, builder.toString());

        return TEMPLATE_HOME;
    }
}
