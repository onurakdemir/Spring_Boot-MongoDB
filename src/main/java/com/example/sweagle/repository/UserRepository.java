package com.example.sweagle.repository;


import com.example.sweagle.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
