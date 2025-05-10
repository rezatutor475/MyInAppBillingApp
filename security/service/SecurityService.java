package com.myinappbilling.security.service;

import com.myinappbilling.security.model.SecurityToken;
import com.myinappbilling.security.model.AuthAttempt;
import com.myinappbilling.security.model.VerificationStatus;
import com.myinappbilling.security.verifier.TokenVerifier;
import com.myinappbilling.security.verifier.CredentialVerifier;
import com.myinappbilling.security.verifier.SignatureVerifier;

import java.security.PublicKey;
import java.time.LocalDateTime;

/**
 * Handles security-related operations such as token validation and user authentication.
 */
public class SecurityService {

    private final TokenVerifier tokenVerifier;
    private final CredentialVerifier credentialVerifier;
    private final SignatureVerifier signatureVerifier;

    public SecurityService() {
        this.tokenVerifier = new TokenVerifier();
        this.credentialVerifier = new CredentialVerifier();
        this.signatureVerifier = new SignatureVerifier();
    }

    /**
     * Validates a security token.
     *
     * @param token The security token to validate
     * @return VerificationStatus indicating result
     */
    public VerificationStatus validateToken(SecurityToken token) {
        boolean isValid = tokenVerifier.verify(token);
        return isValid ? VerificationStatus.SUCCESS : VerificationStatus.FAILURE;
    }

    /**
     * Verifies user credentials.
     *
     * @param attempt The authentication attempt
     * @return VerificationStatus indicating result
     */
    public VerificationStatus verifyCredentials(AuthAttempt attempt) {
        boolean isVerified = credentialVerifier.verify(attempt.getUsername(), attempt.getPassword());
        return isVerified ? VerificationStatus.SUCCESS : VerificationStatus.FAILURE;
    }

    /**
     * Verifies a digital signature.
     *
     * @param data      The original data
     * @param signature The signature in Base64
     * @param publicKey The public key used for verification
     * @return VerificationStatus indicating result
     */
    public VerificationStatus verifyDigitalSignature(String data, String signature, PublicKey publicKey) {
        boolean result = signatureVerifier.verifySignature(data, signature, publicKey);
        return result ? VerificationStatus.SUCCESS : VerificationStatus.FAILURE;
    }

    /**
     * Checks if a digital signature string is Base64-encoded.
     *
     * @param signature The signature string
     * @return true if Base64, false otherwise
     */
    public boolean isBase64Signature(String signature) {
        return signatureVerifier.isValidBase64(signature);
    }

    /**
     * Logs a failed authentication attempt for auditing and tracking.
     *
     * @param attempt The failed authentication attempt
     */
    public void logFailedAttempt(AuthAttempt attempt) {
        System.out.println("[Security Log] Failed login attempt: " + attempt.getUsername() + " at " + LocalDateTime.now());
    }

    /**
     * Validates a token and logs the outcome.
     *
     * @param token The security token to validate
     * @return true if valid, false otherwise
     */
    public boolean validateAndLogToken(SecurityToken token) {
        boolean valid = tokenVerifier.verify(token);
        System.out.println("[Security Log] Token validation for token ID " + token.getTokenId() + ": " + (valid ? "SUCCESS" : "FAILURE"));
        return valid;
    }
}
