package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String profession;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SectorType sector;

    public JobApplication() {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<div class=\"col-md-3 d-flex flex-column text-center\">")
                .append("<img src=\""
                        + SectorType.getSimpleValue(this.getSector()) + ".jpg\" class=\"img-thumbnail\" width=\"300\" height=\"300\">")
                .append("<h5 class=\"text-center\">" + this.getProfession() + "</h5>")
                .append("<a href=\"/jobs/details/" + this.getId() + "\" class=\"btn btn-info\">Details</a>")
                .append("<a href=\"/jobs/delete/" + this.getId() + "\" class=\"btn btn-danger\">Delete</a>")
                .append("</div>");

        return builder.toString();
    }

    public String getId() {
        return id;
    }

    public SectorType getSector() {
        return sector;
    }

    public void setSector(SectorType sector) {
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
