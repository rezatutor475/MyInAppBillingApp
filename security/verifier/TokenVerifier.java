package com.myinappbilling.security.verifier;

import com.myinappbilling.security.model.SecurityToken;

import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Verifies the integrity and validity of security tokens.
 */
public class TokenVerifier {

    private static final long TOKEN_EXPIRY_THRESHOLD_MS = 5 * 60 * 1000; // 5 minutes
    private static final Logger logger = Logger.getLogger(TokenVerifier.class.getName());

    /**
     * Verifies if a security token is valid.
     *
     * @param token the SecurityToken to be verified
     * @return true if the token is valid; false otherwise
     */
    public boolean verifyToken(SecurityToken token) {
        if (token == null) {
            logger.warning("Token is null.");
            return false;
        }

        if (isExpired(token)) {
            logger.info("Token is expired.");
            return false;
        }

        if (!isSignatureValid(token)) {
            logger.warning("Invalid token signature.");
            return false;
        }

        return true;
    }

    /**
     * Checks if the token has expired.
     *
     * @param token the SecurityToken
     * @return true if expired; false otherwise
     */
    private boolean isExpired(SecurityToken token) {
        long currentTime = new Date().getTime();
        boolean expired = currentTime - token.getIssuedAt() > TOKEN_EXPIRY_THRESHOLD_MS;
        logger.fine("Token issued at: " + token.getIssuedAt() + ", current time: " + currentTime + ", expired: " + expired);
        return expired;
    }

    /**
     * Validates the signature of the token.
     * For demonstration purposes, this is a placeholder.
     *
     * @param token the SecurityToken
     * @return true if the signature is valid; false otherwise
     */
    private boolean isSignatureValid(SecurityToken token) {
        String expectedSignature = generateSignature(token.getPayload());
        boolean valid = expectedSignature.equals(token.getSignature());
        logger.fine("Expected signature: " + expectedSignature + ", actual signature: " + token.getSignature());
        return valid;
    }

    /**
     * Generates a mock signature from a token payload.
     *
     * @param payload the payload of the token
     * @return a base64-encoded string representing the signature
     */
    private String generateSignature(String payload) {
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

    /**
     * Logs the verification result of a token.
     *
     * @param token the SecurityToken
     * @param result the result of verification
     */
    public void logVerification(SecurityToken token, boolean result) {
        logger.log(Level.INFO, "Token verification result for payload [{0}]: {1}", new Object[]{token.getPayload(), result});
    }

    /**
     * Returns the remaining time before token expiry in milliseconds.
     *
     * @param token the SecurityToken
     * @return time in milliseconds before expiry
     */
    public long getTimeUntilExpiry(SecurityToken token) {
        long currentTime = new Date().getTime();
        return Math.max(0, TOKEN_EXPIRY_THRESHOLD_MS - (currentTime - token.getIssuedAt()));
    }
}
