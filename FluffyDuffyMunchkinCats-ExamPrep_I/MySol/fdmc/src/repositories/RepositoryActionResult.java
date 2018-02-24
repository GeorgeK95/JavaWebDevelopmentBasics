package repositories;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public class RepositoryActionResult {
    private Object result;

    public RepositoryActionResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
