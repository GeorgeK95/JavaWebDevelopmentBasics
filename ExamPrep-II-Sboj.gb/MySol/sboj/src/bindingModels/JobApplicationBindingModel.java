package bindingModels;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public class JobApplicationBindingModel {

    private String sector;
    private String profession;
    private Double salary;
    private String description;

    public JobApplicationBindingModel() {
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
