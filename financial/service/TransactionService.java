package com.myinappbilling.financial.service;

import com.myinappbilling.financial.model.Transaction;
import com.myinappbilling.financial.repository.TransactionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to transactions.
 */
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Creates a new transaction.
     * @param transaction The transaction to be created.
     */
    public void createTransaction(Transaction transaction) {
        transactionRepository.addTransaction(transaction);
    }

    /**
     * Retrieves a transaction by ID.
     * @param transactionId The ID of the transaction.
     * @return Optional containing the transaction, if found.
     */
    public Optional<Transaction> getTransactionById(String transactionId) {
        return transactionRepository.getTransactionById(transactionId);
    }

    /**
     * Returns all transactions.
     * @return List of transactions.
     */
    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    /**
     * Returns transactions for a specific user.
     * @param userId The user ID.
     * @return List of user transactions.
     */
    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionRepository.getTransactionsByUserId(userId);
    }

    /**
     * Returns transactions within a specific date range.
     * @param startDate Start date.
     * @param endDate End date.
     * @return List of transactions in the date range.
     */
    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.getTransactionsByDateRange(startDate, endDate);
    }

    /**
     * Returns transactions above a certain amount.
     * @param amount Minimum amount.
     * @return List of transactions above the specified amount.
     */
    public List<Transaction> getTransactionsAboveAmount(double amount) {
        return transactionRepository.getTransactionsAboveAmount(amount);
    }

    /**
     * Returns transactions by status (e.g., SUCCESS, FAILED).
     * @param status The status to filter by.
     * @return List of matching transactions.
     */
    public List<Transaction> getTransactionsByStatus(String status) {
        return transactionRepository.getTransactionsByStatus(status);
    }

    /**
     * Deletes a transaction.
     * @param transactionId The ID of the transaction to delete.
     * @return True if deletion was successful, false otherwise.
     */
    public boolean deleteTransaction(String transactionId) {
        return transactionRepository.deleteTransaction(transactionId);
    }

    /**
     * Updates an existing transaction.
     * @param transaction The updated transaction.
     */
    public void updateTransaction(Transaction transaction) {
        transactionRepository.updateTransaction(transaction);
    }

    /**
     * Verifies if a transaction exists by its ID.
     * @param transactionId The transaction ID to check.
     * @return True if transaction exists, false otherwise.
     */
    public boolean transactionExists(String transactionId) {
        return transactionRepository.getTransactionById(transactionId).isPresent();
    }

    /**
     * Clears all transactions (useful for testing or data reset).
     */
    public void clearAllTransactions() {
        transactionRepository.clearAllTransactions();
    }
}
