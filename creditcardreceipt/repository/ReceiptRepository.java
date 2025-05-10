package com.myinappbilling.creditcardreceipt.repository;

import com.myinappbilling.creditcardreceipt.model.Receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository class for managing Receipt data.
 */
public class ReceiptRepository {

    private final Map<String, Receipt> receiptDatabase;

    public ReceiptRepository() {
        this.receiptDatabase = new HashMap<>();
    }

    /**
     * Saves a receipt into the repository.
     *
     * @param receipt the receipt to save
     */
    public void saveReceipt(Receipt receipt) {
        if (receipt != null && receipt.getReceiptId() != null) {
            receiptDatabase.put(receipt.getReceiptId(), receipt);
        }
    }

    /**
     * Retrieves a receipt by its ID.
     *
     * @param receiptId the ID of the receipt
     * @return the found receipt, or null if not found
     */
    public Receipt getReceiptById(String receiptId) {
        return receiptDatabase.get(receiptId);
    }

    /**
     * Retrieves all stored receipts.
     *
     * @return a list of all receipts
     */
    public List<Receipt> getAllReceipts() {
        return new ArrayList<>(receiptDatabase.values());
    }

    /**
     * Deletes a receipt by its ID.
     *
     * @param receiptId the ID of the receipt to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteReceipt(String receiptId) {
        if (receiptDatabase.containsKey(receiptId)) {
            receiptDatabase.remove(receiptId);
            return true;
        }
        return false;
    }

    /**
     * Clears all stored receipts.
     */
    public void clearAllReceipts() {
        receiptDatabase.clear();
    }

    /**
     * Searches receipts by merchant name.
     *
     * @param merchantName the merchant name to search
     * @return list of receipts matching the merchant name
     */
    public List<Receipt> searchReceiptsByMerchant(String merchantName) {
        return receiptDatabase.values().stream()
                .filter(receipt -> receipt.getMerchantName().equalsIgnoreCase(merchantName))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves receipts within a specific amount range.
     *
     * @param minAmount minimum transaction amount
     * @param maxAmount maximum transaction amount
     * @return list of receipts within the amount range
     */
    public List<Receipt> getReceiptsByAmountRange(double minAmount, double maxAmount) {
        return receiptDatabase.values().stream()
                .filter(receipt -> receipt.getTotalAmount() >= minAmount && receipt.getTotalAmount() <= maxAmount)
                .collect(Collectors.toList());
    }
}
