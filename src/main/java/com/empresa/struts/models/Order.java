package com.empresa.struts.models;

public class Order {
    private int id;
    private int itemId;
    private String itemName;
    private int qty;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
}