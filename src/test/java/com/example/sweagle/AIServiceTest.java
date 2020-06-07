package com.example.sweagle;

import com.example.sweagle.enums.PredictType;
import com.example.sweagle.model.Message;
import com.example.sweagle.model.User;
import com.example.sweagle.repository.MessageRepository;
import com.example.sweagle.repository.UserRepository;
import com.example.sweagle.service.AIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class AIServiceTest {

    @Autowired
    AIService aiService;

    @Autowired
    MessageRepository messageRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        List<Message> msgList = new ArrayList<>();
        Message msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-01 00:00:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-01 01:00:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-02 00:10:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-03 10:00:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-02 15:15:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-03 12:00:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-04 20:00:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-04 08:10:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-04 09:45:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-05 18:00:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-05 19:00:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-04 06:50:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-06 17:21:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-04 19:10:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-07 12:20:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-07 16:37:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-07 09:20:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-06-07 11:05:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-08 16:37:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-08 09:20:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2018-01-08 11:05:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);
        msg1 = new Message("2", "Trial1", "Trial Trial", convertLocalDateToDate("2020-06-07 11:05:01"));
        msg1.setSenderId("1");
        msgList.add(msg1);

        // Save all messages.
        messageRepository.saveAll(msgList);
    }

    @Test
    public void testDailyPredictionExist() {
        Long numberOfMessages = aiService.predict(PredictType.DAY);
        assertThat(numberOfMessages).isGreaterThan(0);
    }

    @Test
    public void testWeeklyPredictionExist() {
        Long numberOfMessages = aiService.predict(PredictType.WEEK);
        System.out.println(numberOfMessages);
        assertThat(numberOfMessages).isGreaterThan(0);
    }

    @Test()
    public void testIfUserHasPredictions() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(new User("Trial")));
    }
    // Utils
    private Date convertLocalDateToDate(String date) {
        return Timestamp.valueOf(date);
    }
}
