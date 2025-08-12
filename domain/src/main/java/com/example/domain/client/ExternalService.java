package com.example.domain.client;

/**
 * Defines the contract for an external service.
 * The application layer will depend on this interface, not the concrete implementation.
 */
public interface ExternalService {

    /**
     * Gets some information from an external service.
     *
     * @param id The identifier to query for.
     * @return The information retrieved from the external service.
     */
    String getExternalInfo(String id);
}
