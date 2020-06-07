package com.example.sweagle.service;

import com.example.sweagle.model.Message;
import com.example.sweagle.model.User;
import com.example.sweagle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    public MessageService messageService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> userExists(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Message sendMessage(Message message) {
        if (userExists(message.getReceiverId()).isPresent() && userExists(message.getSenderId()).isPresent()) {
            return messageService.sendMessage(message);
        }
        return null;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
