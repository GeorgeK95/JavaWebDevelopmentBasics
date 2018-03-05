package bookhut.entities;

import bookhut.utils.IdGenerator;

import java.util.Date;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private int pages;
    private Date creationDate;

    public Book() {
        this.setId();
        this.setCreationDate();
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int getPages() {
        return pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    private void setCreationDate() {
        this.creationDate = new Date();
    }

    private void setId() {
        this.id = IdGenerator.generateId();
    }

}
