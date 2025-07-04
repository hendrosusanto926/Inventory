package com.empresa.struts.models;
public class Report {
    private int itemId;
    private String itemName;
    private double price;
    private int totalOrder;
    private int totalInventory;
    private double totalRevenue;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(int totalOrder) {
		this.totalOrder = totalOrder;
	}
	public int getTotalInventory() {
		return totalInventory;
	}
	public void setTotalInventory(int totalInventory) {
		this.totalInventory = totalInventory;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

    // Getters & Setters
}