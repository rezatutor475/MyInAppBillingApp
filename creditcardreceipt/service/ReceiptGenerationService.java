package com.myinappbilling.creditcardreceipt.service;

import com.myinappbilling.creditcardreceipt.model.Receipt;
import com.myinappbilling.creditcardreceipt.model.ReceiptItem;
import com.myinappbilling.creditcardreceipt.model.TransactionInfo;
import com.myinappbilling.creditcardreceipt.repository.ReceiptRepository;
import com.myinappbilling.creditcardreceipt.util.ReceiptUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for generating and managing receipts based on transaction information.
 */
public class ReceiptGenerationService {

    private final ReceiptRepository receiptRepository;

    public ReceiptGenerationService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    /**
     * Generates a receipt for the given transaction and saves it.
     *
     * @param transactionInfo the transaction information
     * @return the generated receipt
     */
    public Receipt generateReceipt(TransactionInfo transactionInfo) {
        if (!validateTransaction(transactionInfo)) {
            throw new IllegalArgumentException("Invalid transaction information.");
        }

        List<ReceiptItem> receiptItems = createReceiptItems(transactionInfo);
        Receipt receipt = new Receipt(
                UUID.randomUUID().toString(),
                transactionInfo,
                receiptItems,
                new Date(),
                ReceiptUtils.calculateTotalAmount(receiptItems)
        );
        receiptRepository.saveReceipt(receipt);
        return receipt;
    }

    /**
     * Creates receipt items based on the transaction information.
     *
     * @param transactionInfo the transaction info
     * @return list of receipt items
     */
    private List<ReceiptItem> createReceiptItems(TransactionInfo transactionInfo) {
        List<ReceiptItem> items = new ArrayList<>();
        items.add(new ReceiptItem(
                transactionInfo.getMerchantName() + " Purchase",
                1,
                transactionInfo.getTransactionAmount()
        ));
        return items;
    }

    /**
     * Regenerates a receipt if modifications are needed.
     *
     * @param existingReceipt the existing receipt to modify
     * @param updatedItems updated receipt items
     * @return updated receipt
     */
    public Receipt regenerateReceipt(Receipt existingReceipt, List<ReceiptItem> updatedItems) {
        Receipt updatedReceipt = new Receipt(
                existingReceipt.getReceiptId(),
                existingReceipt.getTransactionInfo(),
                updatedItems,
                new Date(),
                ReceiptUtils.calculateTotalAmount(updatedItems)
        );
        receiptRepository.updateReceipt(updatedReceipt);
        return updatedReceipt;
    }

    /**
     * Validates a transaction before generating a receipt.
     *
     * @param transactionInfo the transaction information
     * @return true if valid, false otherwise
     */
    public boolean validateTransaction(TransactionInfo transactionInfo) {
        return transactionInfo != null && transactionInfo.isValidTransaction();
    }

    /**
     * Deletes a receipt based on its ID.
     *
     * @param receiptId the ID of the receipt to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteReceipt(String receiptId) {
        return receiptRepository.deleteReceiptById(receiptId);
    }

    /**
     * Fetches a receipt by its ID.
     *
     * @param receiptId the receipt ID
     * @return the found receipt or null
     */
    public Receipt fetchReceiptById(String receiptId) {
        return receiptRepository.getReceiptById(receiptId);
    }

    /**
     * Lists all receipts stored in the repository.
     *
     * @return list of all receipts
     */
    public List<Receipt> listAllReceipts() {
        return receiptRepository.getAllReceipts();
    }

    /**
     * Calculates total amount for a receipt by its ID.
     *
     * @param receiptId the receipt ID
     * @return total amount or 0 if not found
     */
    public double calculateTotalAmountForReceipt(String receiptId) {
        Receipt receipt = receiptRepository.getReceiptById(receiptId);
        if (receipt != null) {
            return ReceiptUtils.calculateTotalAmount(receipt.getReceiptItems());
        }
        return 0.0;
    }
}
