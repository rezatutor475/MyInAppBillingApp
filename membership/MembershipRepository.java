package com.myinappbilling.repository;

import com.myinappbilling.model.MembershipPlan;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;

/**
 * MembershipRepository manages membership plans in the repository.
 * It supports operations such as adding, updating, deleting, and retrieving membership plans.
 */
public class MembershipRepository {

    // In-memory storage for membership plans (simulating a database)
    private final Map<String, MembershipPlan> membershipDatabase;

    public MembershipRepository() {
        this.membershipDatabase = new HashMap<>();
    }

    // Existing methods omitted for brevity...

    /**
     * Retrieves all membership plans in the repository, sorted by price in ascending order.
     *
     * @return A sorted list of membership plans based on price.
     */
    public List<MembershipPlan> getAllMembershipPlansSortedByPrice() {
        return membershipDatabase.values().stream()
                .sorted((plan1, plan2) -> {
                    double price1 = Double.parseDouble(plan1.getPrice().replace("$", ""));
                    double price2 = Double.parseDouble(plan2.getPrice().replace("$", ""));
                    return Double.compare(price1, price2);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves membership plans that are about to expire soon (within a specific duration).
     *
     * @param daysUntilExpiration The number of days within which the plan expires.
     * @return A list of membership plans expiring soon.
     */
    public List<MembershipPlan> getExpiringMembershipPlansSoon(int daysUntilExpiration) {
        List<MembershipPlan> result = new ArrayList<>();
        Date currentDate = new Date();
        for (MembershipPlan plan : membershipDatabase.values()) {
            Date expirationDate = plan.getExpirationDate(); // Assuming the expiration date is a field in the plan
            if (expirationDate != null && expirationDate.before(new Date(currentDate.getTime() + (daysUntilExpiration * 86400000L)))) {
                result.add(plan);
            }
        }
        return result;
    }

    /**
     * Retrieves membership plans based on a specified duration (e.g., "1 month", "6 months").
     *
     * @param duration The duration of the membership plans (e.g., "1 month", "1 year").
     * @return A list of membership plans matching the duration.
     */
    public List<MembershipPlan> getMembershipPlansByDuration(String duration) {
        List<MembershipPlan> result = new ArrayList<>();
        for (MembershipPlan plan : membershipDatabase.values()) {
            if (plan.getDuration().equalsIgnoreCase(duration)) {
                result.add(plan);
            }
        }
        return result;
    }

    /**
     * Bulk add or update membership plans.
     * If a plan already exists in the repository, it will be updated.
     *
     * @param membershipPlans A list of membership plans to add or update.
     */
    public void bulkAddOrUpdateMembershipPlans(List<MembershipPlan> membershipPlans) {
        if (membershipPlans == null || membershipPlans.isEmpty()) {
            throw new IllegalArgumentException("The list of membership plans cannot be null or empty.");
        }

        for (MembershipPlan plan : membershipPlans) {
            if (plan != null && plan.getPlanName() != null && !plan.getPlanName().isEmpty()) {
                membershipDatabase.put(plan.getPlanName(), plan);
            } else {
                throw new IllegalArgumentException("Each membership plan must have a valid plan name.");
            }
        }
    }

    /**
     * Retrieves membership plans by their price.
     *
     * @param price The price of the membership plans to retrieve.
     * @return A list of membership plans with the specified price.
     */
    public List<MembershipPlan> getMembershipPlansByPrice(double price) {
        List<MembershipPlan> result = new ArrayList<>();
        for (MembershipPlan plan : membershipDatabase.values()) {
            try {
                double planPrice = Double.parseDouble(plan.getPrice().replace("$", ""));
                if (planPrice == price) {
                    result.add(plan);
                }
            } catch (NumberFormatException e) {
                // Handle invalid price format for the plan
                System.err.println("Invalid price format for plan " + plan.getPlanName());
            }
        }
        return result;
    }

    /**
     * Retrieves membership plans that are either active or inactive, based on the specified active status.
     *
     * @param isActive Whether to filter for active or inactive plans.
     * @return A list of membership plans that match the specified active status.
     */
    public List<MembershipPlan> getMembershipPlansByActiveStatus(boolean isActive) {
        List<MembershipPlan> result = new ArrayList<>();
        for (MembershipPlan plan : membershipDatabase.values()) {
            if (plan.isActive() == isActive) {
                result.add(plan);
            }
        }
        return result;
    }

    /**
     * Removes expired membership plans (plans that are past their expiration date).
     *
     * @return The number of expired plans removed.
     */
    public int removeExpiredMembershipPlans() {
        int removedCount = 0;
        Date currentDate = new Date();
        List<String> expiredPlans = new ArrayList<>();
        
        // Identify expired plans
        for (Map.Entry<String, MembershipPlan> entry : membershipDatabase.entrySet()) {
            MembershipPlan plan = entry.getValue();
            if (plan.getExpirationDate() != null && plan.getExpirationDate().before(currentDate)) {
                expiredPlans.add(entry.getKey());
            }
        }
        
        // Remove expired plans
        for (String planName : expiredPlans) {
            membershipDatabase.remove(planName);
            removedCount++;
        }
        
        return removedCount;
    }

    /**
     * Clears all membership plans in the repository (useful for testing or resetting the data).
     */
    public void clearAllMembershipPlans() {
        membershipDatabase.clear();
    }
}
