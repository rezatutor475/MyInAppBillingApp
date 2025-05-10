package com.myinappbilling.model;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * MembershipPlan represents a membership plan for users.
 * This class holds information about the plan's details such as the name,
 * duration, price, features, and whether the plan is a subscription or not.
 */
public class MembershipPlan {

    private String planName;
    private String planDescription;
    private String price;
    private String priceCurrencyCode;
    private String subscriptionPeriod;
    private boolean isSubscription;
    private boolean isActive;
    private String planFeatures;
    private Date expirationDate;  // For subscription-based plans

    // Constructor for non-subscription plans
    public MembershipPlan(String planName, String planDescription, String price, String priceCurrencyCode, String planFeatures) {
        this.planName = planName;
        this.planDescription = planDescription;
        this.price = price;
        this.priceCurrencyCode = priceCurrencyCode;
        this.planFeatures = planFeatures;
        this.isSubscription = false;
        this.subscriptionPeriod = null;
        this.isActive = true; // Assuming plan is active by default
    }

    // Constructor for subscription plans
    public MembershipPlan(String planName, String planDescription, String price, String priceCurrencyCode, String subscriptionPeriod, String planFeatures) {
        this.planName = planName;
        this.planDescription = planDescription;
        this.price = price;
        this.priceCurrencyCode = priceCurrencyCode;
        this.subscriptionPeriod = subscriptionPeriod;
        this.planFeatures = planFeatures;
        this.isSubscription = true;
        this.isActive = true; // Assuming plan is active by default
        this.expirationDate = calculateExpirationDate(subscriptionPeriod); // Set expiration date
    }

    // Getters and Setters
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
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
        this.expirationDate = calculateExpirationDate(subscriptionPeriod); // Recalculate expiration date
    }

    public boolean isSubscription() {
        return isSubscription;
    }

    public void setSubscription(boolean subscription) {
        isSubscription = subscription;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPlanFeatures() {
        return planFeatures;
    }

    public void setPlanFeatures(String planFeatures) {
        this.planFeatures = planFeatures;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Returns a human-readable format of the membership plan details.
     *
     * @return A string representation of the membership plan details.
     */
    @Override
    public String toString() {
        return "MembershipPlan{" +
                "planName='" + planName + '\'' +
                ", planDescription='" + planDescription + '\'' +
                ", price='" + price + '\'' +
                ", priceCurrencyCode='" + priceCurrencyCode + '\'' +
                ", subscriptionPeriod='" + subscriptionPeriod + '\'' +
                ", isSubscription=" + isSubscription +
                ", isActive=" + isActive +
                ", planFeatures='" + planFeatures + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }

    /**
     * Compares two MembershipPlan objects for equality based on their plan name.
     *
     * @param obj The object to compare with.
     * @return True if both plans are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MembershipPlan that = (MembershipPlan) obj;

        return planName != null ? planName.equals(that.planName) : that.planName == null;
    }

    /**
     * Generates a hash code based on the plan name.
     *
     * @return The hash code of the plan name.
     */
    @Override
    public int hashCode() {
        return planName != null ? planName.hashCode() : 0;
    }

    /**
     * Activates the membership plan.
     */
    public void activatePlan() {
        setActive(true);
    }

    /**
     * Deactivates the membership plan.
     */
    public void deactivatePlan() {
        setActive(false);
    }

    /**
     * Validates the membership plan to ensure that the price and subscription period (if applicable) are set correctly.
     *
     * @return True if the plan is valid, false otherwise.
     */
    public boolean validatePlan() {
        if (planName == null || planName.isEmpty()) {
            return false;
        }
        if (price == null || price.isEmpty() || priceCurrencyCode == null || priceCurrencyCode.isEmpty()) {
            return false;
        }

        // If the plan is a subscription, ensure the subscription period is set.
        if (isSubscription && (subscriptionPeriod == null || subscriptionPeriod.isEmpty())) {
            return false;
        }

        return true;
    }

    /**
     * Returns a formatted string for the price with the currency symbol.
     *
     * @return Formatted price string.
     */
    public String getFormattedPrice() {
        return priceCurrencyCode + " " + price;
    }

    /**
     * Compares two membership plans and returns a message indicating which plan is cheaper.
     *
     * @param otherPlan The other membership plan to compare with.
     * @return A string indicating which plan is cheaper or if they are the same price.
     */
    public String comparePrice(MembershipPlan otherPlan) {
        try {
            double thisPrice = Double.parseDouble(this.price);
            double otherPrice = Double.parseDouble(otherPlan.getPrice());

            if (thisPrice < otherPrice) {
                return this.planName + " is cheaper.";
            } else if (thisPrice > otherPrice) {
                return otherPlan.getPlanName() + " is cheaper.";
            } else {
                return "Both plans are the same price.";
            }
        } catch (NumberFormatException e) {
            return "Invalid price format.";
        }
    }

    // Additional Methods

    /**
     * Updates the details of the membership plan.
     *
     * @param price            New price of the plan.
     * @param subscriptionPeriod New subscription period (if applicable).
     * @param planFeatures     Updated features for the plan.
     */
    public void updatePlanDetails(String price, String subscriptionPeriod, String planFeatures) {
        this.price = price;
        this.subscriptionPeriod = subscriptionPeriod;
        this.planFeatures = planFeatures;
        this.expirationDate = calculateExpirationDate(subscriptionPeriod); // Update expiration date
    }

    /**
     * Calculate the expiration date for a subscription plan based on the subscription period.
     *
     * @param subscriptionPeriod The period of the subscription (e.g., "1 month", "1 year").
     * @return The expiration date as a Date object.
     */
    private Date calculateExpirationDate(String subscriptionPeriod) {
        // Simple logic for calculating expiration date based on the subscription period.
        // You can enhance this logic to handle more complex periods (e.g., "3 months", "6 months").
        Date currentDate = new Date();
        long expirationTime = currentDate.getTime();

        if ("1 year".equalsIgnoreCase(subscriptionPeriod)) {
            expirationTime += 365L * 24 * 60 * 60 * 1000;  // Adding 1 year
        } else if ("1 month".equalsIgnoreCase(subscriptionPeriod)) {
            expirationTime += 30L * 24 * 60 * 60 * 1000;  // Adding 1 month
        }

        return new Date(expirationTime);
    }

    /**
     * Checks if the subscription plan has expired.
     *
     * @return True if the subscription has expired, false otherwise.
     */
    public boolean hasSubscriptionExpired() {
        if (isSubscription && expirationDate != null) {
            return new Date().after(expirationDate);
        }
        return false;
    }

    /**
     * Calculates the total cost for a subscription plan over the subscription period.
     *
     * @return The total cost as a formatted string.
     */
    public String calculateTotalCost() {
        if (isSubscription) {
            double totalCost = Double.parseDouble(price);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(totalCost);
        }
        return "Not a subscription plan";
    }
}
