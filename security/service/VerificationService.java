package com.myinappbilling.security.service;

import com.myinappbilling.security.model.AuthAttempt;
import com.myinappbilling.security.model.SecurityToken;
import com.myinappbilling.security.model.VerificationStatus;
import com.myinappbilling.security.verifier.CredentialVerifier;
import com.myinappbilling.security.verifier.SignatureVerifier;
import com.myinappbilling.security.verifier.TokenVerifier;

import java.security.PublicKey;
import java.time.Instant;

/**
 * Provides verification services for tokens, credentials, and digital signatures.
 */
public class VerificationService {

    private final TokenVerifier tokenVerifier;
    private final CredentialVerifier credentialVerifier;
    private final SignatureVerifier signatureVerifier;

    public VerificationService() {
        this.tokenVerifier = new TokenVerifier();
        this.credentialVerifier = new CredentialVerifier();
        this.signatureVerifier = new SignatureVerifier();
    }

    /**
     * Verifies the authenticity of a security token.
     *
     * @param token The token to be verified
     * @return VerificationStatus indicating success or failure
     */
    public VerificationStatus verifySecurityToken(SecurityToken token) {
        return tokenVerifier.verify(token) ? VerificationStatus.SUCCESS : VerificationStatus.FAILURE;
    }

    /**
     * Verifies user credentials (e.g., username and password).
     *
     * @param authAttempt The authentication attempt containing credentials
     * @return VerificationStatus indicating result
     */
    public VerificationStatus verifyUserCredentials(AuthAttempt authAttempt) {
        return credentialVerifier.verify(authAttempt.getUsername(), authAttempt.getPassword())
                ? VerificationStatus.SUCCESS
                : VerificationStatus.FAILURE;
    }

    /**
     * Validates a digital signature using the original data and public key.
     *
     * @param data Original data string
     * @param signature Base64-encoded signature
     * @param publicKey Public key for signature verification
     * @return VerificationStatus indicating result
     */
    public VerificationStatus verifySignature(String data, String signature, PublicKey publicKey) {
        return signatureVerifier.verifySignature(data, signature, publicKey)
                ? VerificationStatus.SUCCESS
                : VerificationStatus.FAILURE;
    }

    /**
     * Checks if the digital signature string is in valid Base64 format.
     *
     * @param signature The signature string
     * @return true if valid Base64, false otherwise
     */
    public boolean isSignatureBase64Encoded(String signature) {
        return signatureVerifier.isValidBase64(signature);
    }

    /**
     * Logs authentication attempts with timestamp.
     *
     * @param authAttempt The authentication attempt
     */
    public void logAuthAttempt(AuthAttempt authAttempt) {
        System.out.printf("Auth attempt by user '%s' at %s%n",
                authAttempt.getUsername(), Instant.now().toString());
    }

    /**
     * Checks if the public key is null or improperly formatted.
     *
     * @param publicKey PublicKey to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidPublicKey(PublicKey publicKey) {
        return publicKey != null && publicKey.getEncoded().length > 0;
    }
}
