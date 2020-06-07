package com.example.sweagle;

import com.example.sweagle.controller.UserController;
import com.example.sweagle.model.Message;
import com.example.sweagle.model.User;
import com.example.sweagle.repository.MessageRepository;
import com.example.sweagle.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserController userController;

    @Autowired
    private TestRestTemplate restTemplate;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User("User_1"));
        user2 = userRepository.save(new User("User_2"));

        Message msg = new Message(user2.getId(), "Welcome to our neighbourhood",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum", new Date());
        msg.setDate(new Date());
        msg.setSenderId(user1.getId());

        messageRepository.save(msg);
    }

    @AfterEach
    void tearDown() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testUserHasSentMessages() {
        final String BASE_PATH = "http://localhost:" + randomServerPort + "/users";

        List<Message> result = restTemplate.getForObject(BASE_PATH + "/{userId}/messages?type=sent",
                List.class, user1.getId());

        assertThat(result).hasSize(1);
    }

    @Test
    public void testUserHasReceivedMessages() {
        final String BASE_PATH = "http://localhost:" + randomServerPort + "/users";

        List<Message> result = restTemplate.getForObject(BASE_PATH + "/{userId}/messages?type=received",
                List.class, user2.getId());

        assertThat(result).hasSize(1);
    }

    @Test
    public void testUserHasNotSentMessages() {
        final String BASE_PATH = "http://localhost:" + randomServerPort + "/users";

        List<Message> result = restTemplate.getForObject(BASE_PATH + "/{userId}/messages?type=sent",
                List.class, user2.getId());

        assertThat(result).hasSize(0);
    }

    @Test
    public void testSendMessageSuccessfully() throws URISyntaxException {
        final String BASE_PATH = "http://localhost:" + randomServerPort + "/users/" + user1.getId() + "/messages";
        URI uri = new URI(BASE_PATH);
        Message msg = new Message(user2.getId(), "Meeting request", "Dear, Can we meet today if possible", new Date());
        msg.setDate(new Date());

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Message> request = new HttpEntity<>(msg, headers);

        ResponseEntity<Message> ret = restTemplate.postForEntity(uri, request, Message.class);

        assertThat(ret.getBody() != null);
        assertThat(Objects.requireNonNull(ret.getBody()).getContent().equals(msg.getContent()));

    }

}
