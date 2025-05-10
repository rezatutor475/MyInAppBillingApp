package com.myinappbilling.creditcardreceipt.service;

import com.myinappbilling.creditcardreceipt.model.TransactionInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for fetching and simulating transaction information.
 */
public class TransactionFetchService {

    /**
     * Fetches a list of recent transactions.
     *
     * @return a list of transaction information
     */
    public List<TransactionInfo> fetchRecentTransactions() {
        List<TransactionInfo> transactions = new ArrayList<>();
        transactions.add(createDummyTransaction("Merchant A", 49.99));
        transactions.add(createDummyTransaction("Merchant B", 19.99));
        transactions.add(createDummyTransaction("Merchant C", 5.49));
        transactions.add(createDummyTransaction("Merchant D", 99.99));
        return transactions;
    }

    /**
     * Fetches a transaction by its unique ID.
     *
     * @param transactionId the transaction ID
     * @return the transaction information if found, null otherwise
     */
    public TransactionInfo fetchTransactionById(String transactionId) {
        // Simulated lookup by transaction ID
        if (transactionId != null && !transactionId.isEmpty()) {
            return createDummyTransaction("Merchant Sample", 29.99);
        }
        return null;
    }

    /**
     * Fetches transactions for a specific merchant.
     *
     * @param merchantName the merchant name
     * @return list of transactions associated with the merchant
     */
    public List<TransactionInfo> fetchTransactionsByMerchant(String merchantName) {
        List<TransactionInfo> merchantTransactions = new ArrayList<>();
        if (merchantName != null && !merchantName.isEmpty()) {
            merchantTransactions.add(createDummyTransaction(merchantName, 15.75));
            merchantTransactions.add(createDummyTransaction(merchantName, 45.30));
        }
        return merchantTransactions;
    }

    /**
     * Creates a dummy transaction.
     *
     * @param merchantName name of the merchant
     * @param amount amount of the transaction
     * @return a TransactionInfo object
     */
    private TransactionInfo createDummyTransaction(String merchantName, double amount) {
        return new TransactionInfo(
                UUID.randomUUID().toString(),
                merchantName,
                amount,
                new Date(),
                true
        );
    }

    /**
     * Validates if the fetched transaction is eligible for receipt generation.
     *
     * @param transactionInfo the transaction information
     * @return true if eligible, false otherwise
     */
    public boolean isTransactionEligible(TransactionInfo transactionInfo) {
        return transactionInfo != null && transactionInfo.isValidTransaction() && transactionInfo.getTransactionAmount() > 0;
    }

    /**
     * Counts the total number of transactions fetched.
     *
     * @param transactions the list of transactions
     * @return total count of transactions
     */
    public int countTotalTransactions(List<TransactionInfo> transactions) {
        return transactions != null ? transactions.size() : 0;
    }

    /**
     * Calculates the total transaction amount for a list of transactions.
     *
     * @param transactions the list of transactions
     * @return the total transaction amount
     */
    public double calculateTotalTransactionAmount(List<TransactionInfo> transactions) {
        double total = 0;
        if (transactions != null) {
            for (TransactionInfo transaction : transactions) {
                total += transaction.getTransactionAmount();
            }
        }
        return total;
    }
}
