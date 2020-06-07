package com.example.sweagle.service;

import com.example.sweagle.model.Message;
import com.example.sweagle.model.User;

import java.util.Optional;

/**
 * User operations for messaging middleware
 */
public interface UserService {
    /**
     * Checks if a user exist
     * @param userId unique user id
     * @return Found user or empty
     */
    Optional<User> userExists(String userId);

    /**
     * Sends a message with the provided message body.
     * @param message <code>Message</code>
     * @return if successful same message with the parameter.
     */
    Message sendMessage(final Message message);
}
