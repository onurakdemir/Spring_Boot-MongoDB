package com.example.sweagle.service;

import com.example.sweagle.enums.PredictType;

/**
 * Estimation service for the messages.
 */
public interface AIService {
    /**
     * Predicts the expected number of messages according to <code>PredictType</code>
     * @param type estimation type use <code>PredictType</code>
     * @return Number of messages
     */
    Long predict(PredictType type);
}
