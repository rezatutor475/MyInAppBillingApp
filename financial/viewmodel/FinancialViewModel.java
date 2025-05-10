package com.myinappbilling.financial.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.financial.model.RefundRequest;
import com.myinappbilling.financial.model.RefundStatus;
import com.myinappbilling.financial.model.Transaction;
import com.myinappbilling.financial.service.RefundService;
import com.myinappbilling.financial.service.TransactionService;

import java.util.List;
import java.util.Optional;

/**
 * ViewModel for financial operations, managing UI-related data
 * for transactions and refund requests.
 */
public class FinancialViewModel extends ViewModel {

    private final TransactionService transactionService;
    private final RefundService refundService;

    private final MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();
    private final MutableLiveData<List<RefundRequest>> refundRequests = new MutableLiveData<>();
    private final MutableLiveData<List<RefundRequest>> approvedRefunds = new MutableLiveData<>();
    private final MutableLiveData<List<RefundRequest>> pendingRefunds = new MutableLiveData<>();
    private final MutableLiveData<List<RefundRequest>> rejectedRefunds = new MutableLiveData<>();

    public FinancialViewModel(TransactionService transactionService, RefundService refundService) {
        this.transactionService = transactionService;
        this.refundService = refundService;
        refreshData();
    }

    public LiveData<List<Transaction>> getTransactions() {
        return transactions;
    }

    public LiveData<List<RefundRequest>> getRefundRequests() {
        return refundRequests;
    }

    public LiveData<List<RefundRequest>> getApprovedRefunds() {
        return approvedRefunds;
    }

    public LiveData<List<RefundRequest>> getPendingRefunds() {
        return pendingRefunds;
    }

    public LiveData<List<RefundRequest>> getRejectedRefunds() {
        return rejectedRefunds;
    }

    public void loadAllTransactions() {
        transactions.setValue(transactionService.getAllTransactions());
    }

    public void loadAllRefundRequests() {
        List<RefundRequest> allRequests = refundService.getAllRefundRequests();
        refundRequests.setValue(allRequests);
        pendingRefunds.setValue(refundService.getRefundRequestsByStatus(RefundStatus.PENDING));
        approvedRefunds.setValue(refundService.getRefundRequestsByStatus(RefundStatus.APPROVED));
        rejectedRefunds.setValue(refundService.getRefundRequestsByStatus(RefundStatus.REJECTED));
    }

    public void submitRefundRequest(RefundRequest request) {
        refundService.submitRefundRequest(request);
        loadAllRefundRequests();
    }

    public void approveRefund(String requestId) {
        refundService.approveRefundRequest(requestId);
        loadAllRefundRequests();
    }

    public void rejectRefund(String requestId) {
        refundService.rejectRefundRequest(requestId);
        loadAllRefundRequests();
    }

    public Optional<RefundRequest> getRefundRequestById(String requestId) {
        return refundService.getRefundRequestById(requestId);
    }

    public Optional<Transaction> getTransactionById(String transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    public void deleteRefundRequest(String requestId) {
        refundService.deleteRefundRequest(requestId);
        loadAllRefundRequests();
    }

    public void refreshData() {
        loadAllTransactions();
        loadAllRefundRequests();
    }
} 
