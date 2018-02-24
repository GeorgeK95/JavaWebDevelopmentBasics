package factories;

import bindingModels.JobApplicationBindingModel;
import entities.JobApplication;
import entities.SectorType;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public final class JobApplicationFactory {

    public static JobApplication create(JobApplicationBindingModel jobApplicationBindingModel) {
        JobApplication job = new JobApplication();
        job.setDescription(jobApplicationBindingModel.getDescription());
        job.setProfession(jobApplicationBindingModel.getProfession());
        job.setSalary(jobApplicationBindingModel.getSalary());
        job.setSector(SectorType.valueOf(jobApplicationBindingModel.getSector().toUpperCase()));
        return job;
    }

    private JobApplicationFactory() {
    }
}
