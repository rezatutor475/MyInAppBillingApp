package com.myinappbilling.security.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.security.model.VerificationStatus;
import com.myinappbilling.security.service.VerificationService;

/**
 * ViewModel for managing security-related UI data and business logic.
 */
public class SecurityViewModel extends ViewModel {

    private final VerificationService verificationService;
    private final MutableLiveData<VerificationStatus> verificationStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isVerifying = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(null);

    public SecurityViewModel() {
        this.verificationService = new VerificationService();
    }

    /**
     * Initiates verification based on token and credentials.
     *
     * @param token Security token
     * @param credentials User credentials
     */
    public void verifySecurity(String token, String credentials) {
        isVerifying.setValue(true);
        try {
            VerificationStatus status = verificationService.verify(token, credentials);
            verificationStatusLiveData.setValue(status);
            errorMessage.setValue(null);
        } catch (Exception e) {
            errorMessage.setValue("Verification failed: " + e.getMessage());
        } finally {
            isVerifying.setValue(false);
        }
    }

    /**
     * Exposes LiveData for verification status.
     *
     * @return LiveData of VerificationStatus
     */
    public LiveData<VerificationStatus> getVerificationStatus() {
        return verificationStatusLiveData;
    }

    /**
     * Indicates whether a verification is currently in progress.
     *
     * @return LiveData of Boolean
     */
    public LiveData<Boolean> isVerifying() {
        return isVerifying;
    }

    /**
     * Exposes LiveData for error messages.
     *
     * @return LiveData of error message
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Clears the current verification status and error message.
     */
    public void resetVerificationStatus() {
        verificationStatusLiveData.setValue(null);
        errorMessage.setValue(null);
        isVerifying.setValue(false);
    }
}
