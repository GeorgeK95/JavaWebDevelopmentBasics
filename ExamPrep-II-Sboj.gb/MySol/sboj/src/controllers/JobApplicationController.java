package controllers;

import bindingModels.JobApplicationBindingModel;
import entities.JobApplication;
import entities.SectorType;
import factories.JobApplicationFactory;
import factories.JobRepositoryFactory;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summer.api.*;
import repositories.Repository;

import static utils.Constants.*;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
@Controller
public class JobApplicationController {

    private Repository<JobApplication> jobRepository;

    public JobApplicationController() {
        this.jobRepository = JobRepositoryFactory.create();
    }

    private void setJobVariables(Model model, String id) {
        if (id != null) {
            System.out.println("tuka bushi:" + id);
            JobApplication job = this.jobRepository.findById(id);

            System.out.println("tuka bushi job:" + job.getId());
            model.addAttribute(ID, job.getId());
            model.addAttribute(SECTOR, SectorType.getComplexValue(job.getSector()));
            model.addAttribute(PROFESSION, job.getProfession());
            model.addAttribute(SALARY, job.getSalary());
            model.addAttribute(DESCRIPTION, job.getDescription());
        }
    }

    private void seedCommonAttributes(Model model) {
        model.addAttribute(HEAD_STRING, HEAD);
        model.addAttribute(LOGGED_NAVIGATION_STRING, LOGGED_NAVIGATION);
        model.addAttribute(FOOTER_STRING, FOOTER);
    }

    @GetMapping(route = JOBS_ADD)
    @PreAuthorize(isLoggedIn = true)
    public String addJob(HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model);

        return TEMPLATE_ADD_JOB;
    }

    @PostMapping(route = JOBS_ADD)
    @PreAuthorize(isLoggedIn = true)
    public String addJobProcess(HttpSoletRequest request, JobApplicationBindingModel jobApplicationBindingModel,
                                Model model) {
        this.seedCommonAttributes(model);

        JobApplication job = JobApplicationFactory.create(jobApplicationBindingModel);
        this.jobRepository.create(job);

        return REDIRECT_HOME;
    }

    @GetMapping(route = JOBS_DETAILS)
    @PreAuthorize(isLoggedIn = true)
    public String jobDetails(@PathVariable String id, HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model);

        System.out.println("PODAVAM MU:" + id);
        this.setJobVariables(model, id);

        return TEMPLATE_JOB_DETAILS;
    }


    @GetMapping(route = JOBS_DELETE)
    @PreAuthorize(isLoggedIn = true)
    public String deleteJob(@PathVariable String id, HttpSoletRequest request, Model model) {
        this.seedCommonAttributes(model);

        this.setJobVariables(model, id);

        return TEMPLATE_JOB_DELETE;
    }

    @PostMapping(route = JOBS_DELETE)
    @PreAuthorize(isLoggedIn = true)
    public String deleteJobProcess(@PathVariable String id, HttpSoletRequest request) {
        this.jobRepository.delete(id);
        return REDIRECT_HOME;
    }

}
