package com.myinappbilling.security.verifier;

import java.security.Signature;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Verifies digital signatures to ensure data integrity and authenticity.
 */
public class SignatureVerifier {

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final Logger LOGGER = Logger.getLogger(SignatureVerifier.class.getName());

    /**
     * Verifies the digital signature of the given data using the provided public key.
     *
     * @param data      The original data
     * @param signature The Base64-encoded digital signature
     * @param publicKey The public key to verify the signature
     * @return true if the signature is valid, false otherwise
     */
    public boolean verifySignature(String data, String signature, PublicKey publicKey) {
        if (!isValidBase64(signature)) {
            LOGGER.warning("Invalid Base64 signature format");
            return false;
        }
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(data.getBytes());
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return sig.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            LOGGER.log(Level.SEVERE, "Signature verification failed", e);
            return false;
        }
    }

    /**
     * Checks if a given signature string is Base64-encoded.
     *
     * @param signature The signature string
     * @return true if valid Base64; false otherwise
     */
    public boolean isValidBase64(String signature) {
        try {
            Base64.getDecoder().decode(signature);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Verifies that the signature algorithm is supported.
     *
     * @param algorithm The algorithm to verify
     * @return true if supported; false otherwise
     */
    public boolean isSupportedAlgorithm(String algorithm) {
        try {
            Signature.getInstance(algorithm);
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    /**
     * Logs the result of a signature verification attempt.
     *
     * @param data The data that was verified
     * @param result true if valid; false otherwise
     */
    public void logVerificationResult(String data, boolean result) {
        LOGGER.info("Verification result for data [" + data + "]: " + (result ? "VALID" : "INVALID"));
    }
}
