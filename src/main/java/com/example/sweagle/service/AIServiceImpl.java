package com.example.sweagle.service;

import com.example.sweagle.enums.PredictType;
import com.example.sweagle.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService{

    private final MessageRepository messageRepository;

    public AIServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Long predict(PredictType predictType) {
        if( predictType.equals(PredictType.WEEK)) {
            // Find historical msg count for the day.
            Long historicalMsgCount = messageRepository.aggregateMessageCountPerWeek();
            // Find the current msg count for the day
            Long actualMsgCount = messageRepository.getWeekMessageCount();

            return historicalMsgCount - actualMsgCount;
        } else if( predictType.equals(PredictType.DAY)) {
            // Find historical msg count for the day.
            Long historicalMsgCount = messageRepository.aggregateMessageCountPerDay();
            // Find the current msg count for the day
            Long actualMsgCount = messageRepository.getTodayMessageCount();

            return historicalMsgCount - actualMsgCount;

        } else {
            throw new IllegalArgumentException("Predict type is not supported. Please use 'week' or 'day'");
        }
    }
}
