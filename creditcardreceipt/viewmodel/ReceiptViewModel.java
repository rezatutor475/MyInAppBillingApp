package com.myinappbilling.creditcardreceipt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.creditcardreceipt.model.Receipt;
import com.myinappbilling.creditcardreceipt.repository.ReceiptRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ViewModel for managing UI-related data for Receipts with advanced features.
 */
public class ReceiptViewModel extends ViewModel {

    private final ReceiptRepository receiptRepository;
    private final MutableLiveData<List<Receipt>> receiptsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Receipt> selectedReceiptLiveData = new MutableLiveData<>();

    public ReceiptViewModel() {
        this.receiptRepository = new ReceiptRepository();
        loadReceipts();
    }

    /**
     * Loads all receipts from the repository.
     */
    private void loadReceipts() {
        List<Receipt> receipts = receiptRepository.getAllReceipts();
        receiptsLiveData.setValue(receipts);
    }

    /**
     * Returns LiveData containing the list of receipts.
     *
     * @return LiveData list of receipts
     */
    public LiveData<List<Receipt>> getReceipts() {
        return receiptsLiveData;
    }

    /**
     * Selects a specific receipt.
     *
     * @param receipt the receipt to select
     */
    public void selectReceipt(Receipt receipt) {
        selectedReceiptLiveData.setValue(receipt);
    }

    /**
     * Returns LiveData containing the selected receipt.
     *
     * @return LiveData of the selected receipt
     */
    public LiveData<Receipt> getSelectedReceipt() {
        return selectedReceiptLiveData;
    }

    /**
     * Adds a new receipt and refreshes the list.
     *
     * @param receipt the new receipt to add
     */
    public void addReceipt(Receipt receipt) {
        receiptRepository.saveReceipt(receipt);
        loadReceipts();
    }

    /**
     * Deletes a receipt and refreshes the list.
     *
     * @param receipt the receipt to delete
     */
    public void deleteReceipt(Receipt receipt) {
        receiptRepository.deleteReceipt(receipt);
        loadReceipts();
    }

    /**
     * Filters receipts by a keyword in receipt ID.
     *
     * @param keyword the keyword to search
     */
    public void filterReceipts(String keyword) {
        List<Receipt> allReceipts = receiptRepository.getAllReceipts();
        List<Receipt> filteredReceipts = allReceipts.stream()
                .filter(receipt -> receipt.getReceiptId() != null && receipt.getReceiptId().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        receiptsLiveData.setValue(filteredReceipts);
    }

    /**
     * Sorts receipts by transaction date.
     *
     * @param ascending true for ascending order, false for descending
     */
    public void sortReceiptsByDate(boolean ascending) {
        List<Receipt> currentReceipts = receiptsLiveData.getValue();
        if (currentReceipts == null) return;

        List<Receipt> sortedReceipts = new ArrayList<>(currentReceipts);
        sortedReceipts.sort((r1, r2) -> {
            if (r1.getTransactionInfo() == null || r2.getTransactionInfo() == null) return 0;
            if (ascending) {
                return r1.getTransactionInfo().getTransactionDate().compareTo(r2.getTransactionInfo().getTransactionDate());
            } else {
                return r2.getTransactionInfo().getTransactionDate().compareTo(r1.getTransactionInfo().getTransactionDate());
            }
        });
        receiptsLiveData.setValue(sortedReceipts);
    }

    /**
     * Sorts receipts by total amount.
     *
     * @param ascending true for ascending order, false for descending
     */
    public void sortReceiptsByAmount(boolean ascending) {
        List<Receipt> currentReceipts = receiptsLiveData.getValue();
        if (currentReceipts == null) return;

        List<Receipt> sortedReceipts = new ArrayList<>(currentReceipts);
        sortedReceipts.sort((r1, r2) -> {
            if (r1.getTransactionInfo() == null || r2.getTransactionInfo() == null) return 0;
            if (ascending) {
                return Double.compare(r1.getTransactionInfo().getTotalAmount(), r2.getTransactionInfo().getTotalAmount());
            } else {
                return Double.compare(r2.getTransactionInfo().getTotalAmount(), r1.getTransactionInfo().getTotalAmount());
            }
        });
        receiptsLiveData.setValue(sortedReceipts);
    }
}
