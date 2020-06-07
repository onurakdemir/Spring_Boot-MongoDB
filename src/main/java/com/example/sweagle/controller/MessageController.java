package com.example.sweagle.controller;

import com.example.sweagle.model.Message;
import com.example.sweagle.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/messages")
@Api(value="message", description="General message operations")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // read message details
    @GetMapping("/{msgId}")
    @ApiOperation(value = "Get the message detail",
            response = Message.class)
    public Optional<Message> getMessageDetails(@PathVariable final String msgId) {
        return messageService.getMessageDetails(msgId);
    }
}
