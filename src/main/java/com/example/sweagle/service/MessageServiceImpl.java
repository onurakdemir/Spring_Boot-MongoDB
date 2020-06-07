package com.example.sweagle.service;

import com.example.sweagle.model.Message;
import com.example.sweagle.repository.MessageRepository;
import com.example.sweagle.enums.MessageType;
import com.example.sweagle.resource.MessageMetaData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    public final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageMetaData> getUserMessages(String userId, MessageType msgType) {
        if (msgType == MessageType.RECEIVED) {
            return messageRepository.findByReceiverId(userId)
                    .stream()
                    .map(m -> new MessageMetaData(m.getId(), m.getSenderId(), m.getReceiverId(), m.getSubject())
            ).collect(Collectors.toList());
        } else {
            return messageRepository.findBySenderId(userId)
                    .stream()
                    .map(m -> new MessageMetaData(m.getId(), m.getSenderId(), m.getReceiverId(), m.getSubject())
                    ).collect(Collectors.toList());
        }
    }

    @Override
    public Message sendMessage(Message message) {
        if(message.getSenderId() == null) {
            throw new IllegalArgumentException("Sender is not specified, please check your message.");
        }
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> getMessageDetails(String msgId) {
        return messageRepository.findById(msgId);
    }
}
