package com.myinappbilling.financial.repository;

import com.myinappbilling.financial.model.Transaction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository class for managing transactions.
 */
public class TransactionRepository {

    private final Map<String, Transaction> transactionDatabase = new HashMap<>();

    /**
     * Adds a transaction to the repository.
     * @param transaction The transaction to add.
     */
    public void addTransaction(Transaction transaction) {
        if (transaction == null || transaction.getTransactionId() == null) {
            throw new IllegalArgumentException("Transaction or Transaction ID cannot be null");
        }
        transactionDatabase.put(transaction.getTransactionId(), transaction);
    }

    /**
     * Retrieves a transaction by ID.
     * @param transactionId The ID of the transaction.
     * @return Optional of Transaction
     */
    public Optional<Transaction> getTransactionById(String transactionId) {
        return Optional.ofNullable(transactionDatabase.get(transactionId));
    }

    /**
     * Retrieves all transactions.
     * @return List of all transactions.
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactionDatabase.values());
    }

    /**
     * Updates an existing transaction.
     * @param transaction The transaction to update.
     */
    public void updateTransaction(Transaction transaction) {
        if (!transactionDatabase.containsKey(transaction.getTransactionId())) {
            throw new IllegalArgumentException("Transaction with ID " + transaction.getTransactionId() + " does not exist.");
        }
        transactionDatabase.put(transaction.getTransactionId(), transaction);
    }

    /**
     * Deletes a transaction by ID.
     * @param transactionId The ID of the transaction to delete.
     * @return True if deleted, false if not found.
     */
    public boolean deleteTransaction(String transactionId) {
        return transactionDatabase.remove(transactionId) != null;
    }

    /**
     * Filters transactions by user ID.
     * @param userId The user ID to filter by.
     * @return List of transactions for the user.
     */
    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionDatabase.values().stream()
                .filter(t -> userId.equals(t.getUserId()))
                .collect(Collectors.toList());
    }

    /**
     * Filters transactions by status.
     * @param status The status to filter by.
     * @return List of transactions with the given status.
     */
    public List<Transaction> getTransactionsByStatus(String status) {
        return transactionDatabase.values().stream()
                .filter(t -> status.equalsIgnoreCase(t.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Filters transactions within a given date range.
     * @param startDate Start date (inclusive).
     * @param endDate End date (inclusive).
     * @return List of transactions within the date range.
     */
    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionDatabase.values().stream()
                .filter(t -> {
                    Date date = t.getTransactionDate();
                    return date != null && !date.before(startDate) && !date.after(endDate);
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total amount of all transactions.
     * @return The total transaction amount.
     */
    public double getTotalTransactionAmount() {
        return transactionDatabase.values().stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Clears all transactions (for testing or reset purposes).
     */
    public void clearAllTransactions() {
        transactionDatabase.clear();
    }
}
