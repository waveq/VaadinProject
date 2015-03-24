package com.waveq.entity;

/**
 * Created by Szymon on 2015-03-24.
 */
public class Auction {

    public Auction(int price, String name, String description, String path) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.path = path;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private int price;
    private String name;
    private String description;
    private String path;

}
