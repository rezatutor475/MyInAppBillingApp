package com.myinappbilling.creditcardreceipt.validator;

import com.myinappbilling.creditcardreceipt.model.Receipt;
import com.myinappbilling.creditcardreceipt.model.ReceiptItem;

import java.util.List;
import java.util.Objects;

/**
 * Validator class for validating Receipt data.
 */
public class ReceiptValidator {

    /**
     * Validates the given receipt.
     *
     * @param receipt the receipt to validate
     * @return true if the receipt is valid, false otherwise
     */
    public boolean isValid(Receipt receipt) {
        if (receipt == null) {
            return false;
        }
        if (isEmpty(receipt.getReceiptId())) {
            return false;
        }
        if (receipt.getTransactionInfo() == null) {
            return false;
        }
        if (!areItemsValid(receipt.getItems())) {
            return false;
        }
        return true;
    }

    /**
     * Validates the list of receipt items.
     *
     * @param items the list of receipt items
     * @return true if all items are valid, false otherwise
     */
    private boolean areItemsValid(List<ReceiptItem> items) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        for (ReceiptItem item : items) {
            if (item == null || isEmpty(item.getItemName()) || item.getItemPrice() < 0 || item.getQuantity() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the receipt total amount matches the sum of item prices multiplied by quantities.
     *
     * @param receipt the receipt to check
     * @return true if total matches, false otherwise
     */
    public boolean isTotalAmountCorrect(Receipt receipt) {
        if (receipt == null || receipt.getItems() == null || receipt.getTransactionInfo() == null) {
            return false;
        }
        double sum = receipt.getItems().stream()
                .filter(Objects::nonNull)
                .mapToDouble(item -> item.getItemPrice() * item.getQuantity())
                .sum();
        return Double.compare(sum, receipt.getTransactionInfo().getTotalAmount()) == 0;
    }

    /**
     * Checks if the receipt date is valid.
     *
     * @param receipt the receipt to check
     * @return true if the receipt date is valid, false otherwise
     */
    public boolean isReceiptDateValid(Receipt receipt) {
        if (receipt == null || receipt.getTransactionInfo() == null) {
            return false;
        }
        return receipt.getTransactionInfo().getTransactionDate() != null;
    }

    /**
     * Performs a full validation on the receipt.
     *
     * @param receipt the receipt to validate
     * @return true if all validations pass, false otherwise
     */
    public boolean performFullValidation(Receipt receipt) {
        return isValid(receipt) && isTotalAmountCorrect(receipt) && isReceiptDateValid(receipt);
    }

    /**
     * Utility method to check if a string is null or empty.
     *
     * @param value the string to check
     * @return true if string is null or empty, false otherwise
     */
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Checks if all items in the receipt have a unique name.
     *
     * @param receipt the receipt to validate
     * @return true if all item names are unique, false otherwise
     */
    public boolean areItemNamesUnique(Receipt receipt) {
        if (receipt == null || receipt.getItems() == null) {
            return false;
        }
        return receipt.getItems().stream()
                .filter(Objects::nonNull)
                .map(ReceiptItem::getItemName)
                .filter(Objects::nonNull)
                .distinct()
                .count() == receipt.getItems().size();
    }

    /**
     * Performs an extended validation on the receipt including uniqueness check.
     *
     * @param receipt the receipt to validate
     * @return true if all extended validations pass, false otherwise
     */
    public boolean performExtendedValidation(Receipt receipt) {
        return performFullValidation(receipt) && areItemNamesUnique(receipt);
    }
}
