package com.myinappbilling.model;

/**
 * ProductDetailsModel represents the details of a product (such as a membership SKU)
 * in the in-app purchase system.
 */
public class ProductDetailsModel {

    private String sku;
    private String title;
    private String description;
    private String price;
    private String priceCurrencyCode;
    private String subscriptionPeriod;
    private boolean isSubscription;

    // Constructor for non-subscription products (e.g., one-time purchases)
    public ProductDetailsModel(String sku, String title, String description, String price, String priceCurrencyCode) {
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.price = price;
        this.priceCurrencyCode = priceCurrencyCode;
        this.isSubscription = false;
        this.subscriptionPeriod = null;
    }

    // Constructor for subscription products
    public ProductDetailsModel(String sku, String title, String description, String price, String priceCurrencyCode, String subscriptionPeriod) {
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.price = price;
        this.priceCurrencyCode = priceCurrencyCode;
        this.isSubscription = true;
        this.subscriptionPeriod = subscriptionPeriod;
    }

    // Getters and setters
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public void setPriceCurrencyCode(String priceCurrencyCode) {
        this.priceCurrencyCode = priceCurrencyCode;
    }

    public String getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    public void setSubscriptionPeriod(String subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }

    public boolean isSubscription() {
        return isSubscription;
    }

    public void setSubscription(boolean subscription) {
        isSubscription = subscription;
    }

    /**
     * Returns a human-readable format of the product details.
     * 
     * @return A string representation of the product details.
     */
    @Override
    public String toString() {
        return "ProductDetailsModel{" +
                "sku='" + sku + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", priceCurrencyCode='" + priceCurrencyCode + '\'' +
                ", subscriptionPeriod='" + subscriptionPeriod + '\'' +
                ", isSubscription=" + isSubscription +
                '}';
    }

    /**
     * Compares two ProductDetailsModel objects for equality based on SKU.
     * 
     * @param obj The object to compare with.
     * @return True if both objects are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ProductDetailsModel that = (ProductDetailsModel) obj;

        return sku != null ? sku.equals(that.sku) : that.sku == null;
    }

    /**
     * Generates a hash code based on the SKU.
     * 
     * @return The hash code of the SKU.
     */
    @Override
    public int hashCode() {
        return sku != null ? sku.hashCode() : 0;
    }

    /**
     * Returns a formatted string for the price with currency symbol.
     * 
     * @return Formatted price string.
     */
    public String getFormattedPrice() {
        // Example: "$9.99"
        return priceCurrencyCode + " " + price;
    }

    /**
     * Validates the SKU to ensure it matches a known pattern or list of valid SKUs.
     * 
     * @return True if the SKU is valid.
     */
    public boolean isValidSku() {
        // Example of valid SKUs, this can be extended as needed.
        return sku != null && (sku.equals("sku_GOLD_MEMBERSHIP") || sku.equals("sku_SILVER_MEMBERSHIP"));
    }

    /**
     * Returns a formatted version of the product description.
     * 
     * @return A human-readable product description.
     */
    public String getFormattedDescription() {
        if (description != null && !description.isEmpty()) {
            return description.length() > 50 ? description.substring(0, 50) + "..." : description;
        } else {
            return "No description available.";
        }
    }

    /**
     * Checks if the subscription period is valid (e.g., "1 month", "1 year").
     * 
     * @return True if the subscription period is valid, otherwise false.
     */
    public boolean isValidSubscriptionPeriod() {
        // Example: Valid subscription periods.
        return subscriptionPeriod != null && (subscriptionPeriod.equals("1 month") || subscriptionPeriod.equals("1 year"));
    }

    /**
     * Calculates the discounted price based on a given discount percentage.
     * 
     * @param discountPercentage The discount percentage to apply.
     * @return The discounted price as a string.
     */
    public String getDiscountedPrice(double discountPercentage) {
        try {
            double originalPrice = Double.parseDouble(price);
            double discountedPrice = originalPrice - (originalPrice * discountPercentage / 100);
            return priceCurrencyCode + " " + String.format("%.2f", discountedPrice);
        } catch (NumberFormatException e) {
            return "Invalid price format";
        }
    }

    /**
     * Compares the prices of two products and returns which one is cheaper.
     * 
     * @param otherProduct The other product to compare with.
     * @return A string indicating which product is cheaper or if they are the same price.
     */
    public String comparePrices(ProductDetailsModel otherProduct) {
        try {
            double thisPrice = Double.parseDouble(this.price);
            double otherPrice = Double.parseDouble(otherProduct.getPrice());

            if (thisPrice < otherPrice) {
                return this.title + " is cheaper.";
            } else if (thisPrice > otherPrice) {
                return otherProduct.getTitle() + " is cheaper.";
            } else {
                return "Both products are the same price.";
            }
        } catch (NumberFormatException e) {
            return "Invalid price format.";
        }
    }
}
