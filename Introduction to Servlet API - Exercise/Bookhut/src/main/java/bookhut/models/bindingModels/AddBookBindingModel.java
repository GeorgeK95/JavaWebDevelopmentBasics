package bookhut.models.bindingModels;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class AddBookBindingModel {
    private String title;
    private String author;
    private int pages;

    public AddBookBindingModel() {
    }

    public AddBookBindingModel(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
