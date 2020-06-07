package com.example.sweagle.util;

import com.example.sweagle.enums.MessageType;
import com.example.sweagle.enums.PredictType;

/**
 * String to enum conversions.
 */
public class EnumUtils {
    /**
     * Converts the given value to <code>PredictType</code>
     * @param type the estimation type "day" or "week"
     * @return PredictType enum value or null.
     */
    public static PredictType convertPredictType(String type) {
        PredictType retType;
        try {
            retType = PredictType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            retType = null;
        }
        return retType;
    }

    /**
     * Converts the given value to <code>MessageType</code>
     * @param type the message type "sent" or "received"
     * @return MessageType enum value or null.
     */
    public static MessageType convertMessageType(String type) {
        MessageType retType;
        try {
            retType = MessageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            retType = null;
        }
        return retType;
    }
}
