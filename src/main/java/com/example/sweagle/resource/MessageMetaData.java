package com.example.sweagle.resource;

import lombok.Data;
import lombok.NonNull;

/**
 * Message meta data that is used for listing general message information.
 */
@Data
public class MessageMetaData {
    @NonNull
    private String id;
    @NonNull
    private String senderId;
    @NonNull
    private String receiverId;
    @NonNull
    private String subject;
}
