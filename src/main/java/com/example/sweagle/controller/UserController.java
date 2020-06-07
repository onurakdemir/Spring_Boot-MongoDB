package com.example.sweagle.controller;

import com.example.sweagle.enums.MessageType;
import com.example.sweagle.model.Message;
import com.example.sweagle.resource.MessageMetaData;
import com.example.sweagle.service.MessageService;
import com.example.sweagle.util.EnumUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "user", description = "Message operations for a user")
public class UserController {

    private final MessageService messageService;

    public UserController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{userId}/messages")
    @ApiOperation(value = "Get user messages according to type parameter",
            notes = "Use type=SENT to get all the sent messages, " +
                    "Use type=RECEIVED to get all the received messages",
            response = MessageMetaData.class)
    public List<MessageMetaData> getUserMessages(@PathVariable String userId, @RequestParam final String type) {
        MessageType msgType = EnumUtils.convertMessageType(type);
        if (msgType == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message type is not supported. Please use 'sent' or 'received");
        }
        return messageService.getUserMessages(userId, msgType);
    }

    @PostMapping("/{userId}/messages")
    public Message sendUserMessage(@PathVariable String userId, @RequestBody final Message msg) {
        msg.setSenderId(userId);
        return messageService.sendMessage(msg);
    }
}
