package bookhut.models.bindingModels.factories;

import bookhut.models.bindingModels.AddBookBindingModel;

/**
 * Created by George-Lenovo on 02/17/2018.
 */
public class AddBookBindingModelFactory {

    public static AddBookBindingModel create(String title, String author, int pages) {
        return new AddBookBindingModel(title, author, pages);
    }
}
