package com.example.sweagle.util;

import com.example.sweagle.model.User;
import com.example.sweagle.repository.MessageRepository;
import com.example.sweagle.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Predefined data for the message sending middleware.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private UserRepository userRepository;

    private final MessageRepository messageRepository;

    Logger logger = LoggerFactory.getLogger(DataLoader.class);

    public DataLoader(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void run(String... args){
        LoadUsers();
    }

    private void LoadUsers() {
        userRepository.deleteAll();
        messageRepository.deleteAll();

        User user1 = userRepository.save(new User("Onur"));
        User user2 = userRepository.save(new User("Kaan"));

        logger.info(user1.toString());
        logger.info(user2.toString());
    }
}
