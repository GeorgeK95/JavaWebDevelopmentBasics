package bindingModels;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public class KittenAddBindingModel {
    private String name;
    private Integer age;
    private String breed;

    public KittenAddBindingModel() {
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
