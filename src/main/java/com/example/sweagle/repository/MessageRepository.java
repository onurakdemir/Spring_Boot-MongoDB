package com.example.sweagle.repository;

import com.example.sweagle.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String>, MessageRepositoryCustom {
    List<Message> findBySenderId(String id);

    List<Message> findByReceiverId(String id);
}
