package com.empresa.struts.models;

public class Inventory {
    private int id;
    private int itemId;
    private String itemName; // For display
    private int qty;
    private String type;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}