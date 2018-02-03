package com.company.byTheCake.model;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class Cake {
    private String name;
    private double price;

    public Cake(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
