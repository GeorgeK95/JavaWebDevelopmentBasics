package repositories;

import entities.JobApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public class JobRepository extends BaseRepository implements Repository<JobApplication> {

    @Override
    public void create(JobApplication job) {
        super.execute(actionResult -> {
            super.entityManager.persist(job);
        });
    }

    @Override
    public JobApplication findById(String id) {
        JobApplication job = (JobApplication) super.execute(actionResult -> {
            String findByIdQuery = String.format("SELECT * FROM job_applications j WHERE j.id = '%s'", id);

            Object queryResult = super.getQueryResult(findByIdQuery, JobApplication.class);

            actionResult.setResult(queryResult);
        }).getResult();

        return job;
    }

    @Override
    public JobApplication findByName(String name) {
        return null;
    }

    @Override
    public JobApplication[] findAll() {
        String findAllJobsQuery = "SELECT * FROM job_applications as j";
        List<JobApplication> resultList = new ArrayList<>();
        super.execute(actionResult -> {
            resultList.addAll(super.getResults(findAllJobsQuery, JobApplication.class));
        });

        return resultList.toArray(new JobApplication[resultList.size()]);
    }

    @Override
    public JobApplication findByEmail(String email) {
        return null;
    }

    /*  @Override
      public void delete(String id) {
          JobApplication job = (JobApplication) super.execute(actionResult -> {
              String deleteQuery = String.format("DELETE FROM job_applications WHERE id = '%s'", id);

              super.executeDmlStatementQuery(deleteQuery);
          }).getResult();

      }*/

    @Override
    public void delete(String jobApplicationId) {
        this.execute(actionResult -> {
            JobApplication jobApplication = (JobApplication)

                    this.entityManager
                            .createNativeQuery(
                                    "SELECT * FROM job_applications AS ja WHERE ja.id = \'" + jobApplicationId + "\'", JobApplication.class)
                            .getResultList()
                            .stream()
                            .findFirst()
                            .orElse(null);

            this.entityManager.remove(jobApplication);
        });
    }
}
