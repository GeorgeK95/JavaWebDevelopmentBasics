package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by George-Lenovo on 02/23/2018.
 */

@Entity
@Table(name = "kittens")
public class Kitten {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BreedType breed;

    public Kitten() {
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("<div class=\"kitten col-md-4 justify-content-center\">")
                .append("<img src=\"" + BreedType.getSimpleValue(this.breed) + ".jpg\" class=\"img-thumbnail\">")
                .append("<h6 class=\"text-center\">Name: " + this.getName() + "</h6>")
                .append("<h6 class=\"text-center\">Age: " + this.getAge() + "</h6>")
                .append("<h6 class=\"text-center\">Breed: " + BreedType.getComplexValue(this.breed) + "</h6>")
                .append("</div>");

        return result.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BreedType getBreed() {
        return breed;
    }

    public void setBreed(BreedType breed) {
        this.breed = breed;
    }
}

