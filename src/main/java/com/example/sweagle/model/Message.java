package com.example.sweagle.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Message {

    @Id
    private String id;

    private String senderId;
    @NonNull
    private String receiverId;
    @NonNull
    private String subject;
    @NonNull
    private String content;
    @NonNull
    private Date date;
}
