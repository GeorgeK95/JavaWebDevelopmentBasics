package factories;

import bindingModels.KittenAddBindingModel;
import entities.BreedType;
import entities.Kitten;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public class KittenFactory {
    public static Kitten create(KittenAddBindingModel kittenAddBindingModel) {
        Kitten kitten = new Kitten();
        kitten.setAge(kittenAddBindingModel.getAge());
        kitten.setBreed(BreedType.parseValue(kittenAddBindingModel.getBreed()));
        kitten.setName(kittenAddBindingModel.getName());
        return kitten;
    }

}
