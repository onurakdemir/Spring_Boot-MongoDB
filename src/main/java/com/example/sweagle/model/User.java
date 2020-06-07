package com.example.sweagle.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
public class User {

    public User(String name) {
        this.name = name;
    }

    // Jackson bug workaround.
    public User() {
    }

    @Id
    private String id;
    @NonNull
    private String name;

    //private List<Message> messages;
}
