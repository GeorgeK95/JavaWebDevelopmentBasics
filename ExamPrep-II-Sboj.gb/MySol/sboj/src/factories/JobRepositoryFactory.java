package factories;

import repositories.JobRepository;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public final class JobRepositoryFactory {

    public static JobRepository create() {
        return new JobRepository();
    }

    private JobRepositoryFactory() {
    }
}
