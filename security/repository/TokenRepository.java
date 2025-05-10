package com.myinappbilling.security.repository;

import com.myinappbilling.security.model.SecurityToken;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository class for managing security tokens in memory.
 */
public class TokenRepository {

    private final Map<String, SecurityToken> tokenStore = new HashMap<>();

    /**
     * Saves a security token.
     *
     * @param token SecurityToken object to save
     */
    public void saveToken(SecurityToken token) {
        tokenStore.put(token.getTokenId(), token);
    }

    /**
     * Retrieves a token by its ID.
     *
     * @param tokenId Unique identifier of the token
     * @return Optional containing SecurityToken if found
     */
    public Optional<SecurityToken> getTokenById(String tokenId) {
        return Optional.ofNullable(tokenStore.get(tokenId));
    }

    /**
     * Deletes a token by its ID.
     *
     * @param tokenId Unique identifier of the token to delete
     */
    public void deleteToken(String tokenId) {
        tokenStore.remove(tokenId);
    }

    /**
     * Checks if a token ID exists in the repository.
     *
     * @param tokenId Unique identifier of the token
     * @return true if the token exists, false otherwise
     */
    public boolean exists(String tokenId) {
        return tokenStore.containsKey(tokenId);
    }

    /**
     * Clears all tokens (useful for test or reset scenarios).
     */
    public void clearAllTokens() {
        tokenStore.clear();
    }

    /**
     * Returns the number of tokens currently stored.
     *
     * @return total token count
     */
    public int countTokens() {
        return tokenStore.size();
    }

    /**
     * Retrieves all stored tokens.
     *
     * @return Collection of SecurityToken objects
     */
    public Collection<SecurityToken> getAllTokens() {
        return tokenStore.values();
    }

    /**
     * Updates an existing token.
     *
     * @param tokenId ID of the token to update
     * @param updatedToken new SecurityToken data
     * @return true if update succeeded, false if token not found
     */
    public boolean updateToken(String tokenId, SecurityToken updatedToken) {
        if (tokenStore.containsKey(tokenId)) {
            tokenStore.put(tokenId, updatedToken);
            return true;
        }
        return false;
    }
}
