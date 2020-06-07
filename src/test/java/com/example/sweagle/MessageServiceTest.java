package com.example.sweagle;

import com.example.sweagle.enums.MessageType;
import com.example.sweagle.model.Message;
import com.example.sweagle.model.User;
import com.example.sweagle.repository.MessageRepository;
import com.example.sweagle.repository.UserRepository;
import com.example.sweagle.resource.MessageMetaData;
import com.example.sweagle.service.MessageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    private Message msg;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User("User_1"));
        user2 = userRepository.save(new User("User_2"));

        msg = new Message(user2.getId(), "Welcome to our neighbourhood",
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
    public void testCanGetSentMessagesForUser1() {
        List<MessageMetaData> ret = messageService.getUserMessages(user1.getId(), MessageType.SENT);

        assertThat(ret.size()).isEqualTo(1);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testUser1HasNoReceivedMessages() {
        List<MessageMetaData> ret = messageService.getUserMessages(user1.getId(), MessageType.RECEIVED);

        assertThat(ret.size()).isEqualTo(0);
    }

    @Test
    public void testUser2HasReceivedMessages() {
        List<MessageMetaData> ret = messageService.getUserMessages(user2.getId(), MessageType.RECEIVED);

        assertThat(ret.size()).isEqualTo(1);
    }

    @Test
    public void testSendMessageSuccess() {
        Message msg = new Message(user1.getId(), "Trial", "Please ignore this message", new Date());
        msg.setSenderId(user2.getId());
        msg.setDate(new Date());

        Message retMessage = messageService.sendMessage(msg);
        assertThat(retMessage.getId().equals(msg.getId()));
        assertThat(retMessage.getContent().equals(msg.getContent()));
        assertThat(retMessage.getDate().equals(msg.getDate()));
    }

    @Test
    public void testSendMessageWithMissingFieldThrowsException() {
        Message msg = new Message(user1.getId(), "Trial", "Please ignore this message", new Date());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> messageService.sendMessage(msg));
    }

    @Test
    public void testCanGetMessageDetails() {
        Optional<Message> retMessage = messageService.getMessageDetails(msg.getId());
        assertThat(retMessage.isPresent());
        assertThat(retMessage.get().getId().equals(msg.getId()));
    }
}
