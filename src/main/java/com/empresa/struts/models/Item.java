package com.empresa.struts.models;

public class Item {
    private int id;
    private String name;
    private int price;
    private int remainingStock;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getRemainingStock() { return remainingStock; }
    public void setRemainingStock(int remainingStock) { this.remainingStock = remainingStock; }
}