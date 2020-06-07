package com.example.sweagle;

import com.example.sweagle.controller.MessageController;
import com.example.sweagle.model.Message;
import com.example.sweagle.model.User;
import com.example.sweagle.repository.MessageRepository;
import com.example.sweagle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTests {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageController messageController;

    @Autowired
    private TestRestTemplate restTemplate;

    private Message msg;

    @BeforeEach
    void setUp() {
        User user1 = userRepository.save(new User("User_1"));
        User user2 = userRepository.save(new User("User_2"));

        msg = new Message(user2.getId(), "Welcome to our neighbourhood",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum", new Date());
        msg.setDate(new Date());
        msg.setSenderId(user1.getId());

        messageRepository.save(msg);
    }

    @Test
    public void testGetMessageDetailsSuccess() {
        final String path = "http://localhost:" + randomServerPort + "/messages/{msgId}";

        Message result = restTemplate.getForObject(path,
                Message.class, msg.getId());

        assertThat(result.getId().equals(msg.getId()));
    }
}
