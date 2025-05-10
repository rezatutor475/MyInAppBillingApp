package com.myinappbilling.creditcardreceipt.model;

import java.util.Objects;

/**
 * Model class representing an item in a receipt.
 */
public class ReceiptItem {

    private String itemId;
    private String description;
    private int quantity;
    private double unitPrice;
    private String category;

    public ReceiptItem(String itemId, String description, int quantity, double unitPrice, String category) {
        this.itemId = itemId;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    public boolean isValidItem() {
        return itemId != null && !itemId.isEmpty()
                && description != null && !description.isEmpty()
                && quantity > 0
                && unitPrice >= 0;
    }

    public boolean isDiscounted() {
        return unitPrice < 1.0; // Example threshold for discount items
    }

    public double calculateTax(double taxRate) {
        return getTotalPrice() * taxRate / 100;
    }

    @Override
    public String toString() {
        return "ReceiptItem{" +
                "itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
