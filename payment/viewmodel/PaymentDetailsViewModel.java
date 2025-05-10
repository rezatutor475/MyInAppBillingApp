package com.myinappbilling.payment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.payment.model.PaymentDetails;
import com.myinappbilling.payment.repository.PaymentDetailsRepository;

import java.util.List;

/**
 * ViewModel for handling UI-related data for PaymentDetails.
 */
public class PaymentDetailsViewModel extends ViewModel {

    private final PaymentDetailsRepository repository = new PaymentDetailsRepository();
    private final MutableLiveData<List<PaymentDetails>> paymentDetailsList = new MutableLiveData<>();
    private final MutableLiveData<PaymentDetails> selectedPaymentDetails = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isSaveSuccessful = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUpdateSuccessful = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isDeleteSuccessful = new MutableLiveData<>();

    public LiveData<List<PaymentDetails>> getPaymentDetailsList() {
        return paymentDetailsList;
    }

    public LiveData<PaymentDetails> getSelectedPaymentDetails() {
        return selectedPaymentDetails;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsSaveSuccessful() {
        return isSaveSuccessful;
    }

    public LiveData<Boolean> getIsUpdateSuccessful() {
        return isUpdateSuccessful;
    }

    public LiveData<Boolean> getIsDeleteSuccessful() {
        return isDeleteSuccessful;
    }

    public void loadAllPaymentDetails() {
        isLoading.setValue(true);
        try {
            paymentDetailsList.setValue(repository.findAll());
        } catch (Exception e) {
            errorMessage.setValue("Failed to load payment details: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    public void selectPaymentDetailsById(String id) {
        isLoading.setValue(true);
        try {
            repository.findById(id).ifPresentOrElse(
                selectedPaymentDetails::setValue,
                () -> errorMessage.setValue("Payment details not found for ID: " + id)
            );
        } catch (Exception e) {
            errorMessage.setValue("Error selecting payment details: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    public void savePaymentDetails(PaymentDetails details) {
        isLoading.setValue(true);
        try {
            PaymentDetails saved = repository.save(details);
            loadAllPaymentDetails();
            selectedPaymentDetails.setValue(saved);
            isSaveSuccessful.setValue(true);
        } catch (Exception e) {
            errorMessage.setValue("Failed to save payment details: " + e.getMessage());
            isSaveSuccessful.setValue(false);
        } finally {
            isLoading.setValue(false);
        }
    }

    public void updatePaymentDetails(PaymentDetails details) {
        isLoading.setValue(true);
        try {
            if (repository.update(details)) {
                loadAllPaymentDetails();
                selectedPaymentDetails.setValue(details);
                isUpdateSuccessful.setValue(true);
            } else {
                errorMessage.setValue("Update failed. Payment details not found for ID: " + details.getId());
                isUpdateSuccessful.setValue(false);
            }
        } catch (Exception e) {
            errorMessage.setValue("Error updating payment details: " + e.getMessage());
            isUpdateSuccessful.setValue(false);
        } finally {
            isLoading.setValue(false);
        }
    }

    public void deletePaymentDetails(String id) {
        isLoading.setValue(true);
        try {
            if (repository.deleteById(id)) {
                loadAllPaymentDetails();
                selectedPaymentDetails.setValue(null);
                isDeleteSuccessful.setValue(true);
            } else {
                errorMessage.setValue("Deletion failed. Payment details not found for ID: " + id);
                isDeleteSuccessful.setValue(false);
            }
        } catch (Exception e) {
            errorMessage.setValue("Error deleting payment details: " + e.getMessage());
            isDeleteSuccessful.setValue(false);
        } finally {
            isLoading.setValue(false);
        }
    }

    public void clearError() {
        errorMessage.setValue(null);
    }

    public void refreshData() {
        loadAllPaymentDetails();
    }

    public int getTotalRecordsCount() {
        return repository.count();
    }

    public void clearStatusFlags() {
        isSaveSuccessful.setValue(null);
        isUpdateSuccessful.setValue(null);
        isDeleteSuccessful.setValue(null);
    }
} 
