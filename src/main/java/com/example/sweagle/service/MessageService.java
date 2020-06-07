package com.example.sweagle.service;

import com.example.sweagle.model.Message;
import com.example.sweagle.enums.MessageType;
import com.example.sweagle.resource.MessageMetaData;

import java.util.List;
import java.util.Optional;

/**
 * Message management service.
 */
public interface MessageService {
    /**
     * Gets all the message information
     * @param userId userId ( unique id like phone or email address )
     * @param msgType message type of the query.
     * @return list of message metadata.
     */
    List<MessageMetaData> getUserMessages(final String userId, final MessageType msgType);

    /**
     * Send message using existing user info
     * @param message <code>Message</code> to be sent
     * @return if successful same message return.
     */
    Message sendMessage(final Message message);

    /**
     * Get a message detail for the given message id.
     * @param msgId message id
     * @return found message or empty.
     */
    Optional<Message> getMessageDetails(final String msgId);
}
