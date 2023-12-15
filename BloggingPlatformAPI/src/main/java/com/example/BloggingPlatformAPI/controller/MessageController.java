package com.example.BloggingPlatformAPI.controller;

import com.example.BloggingPlatformAPI.model.Message;
import com.example.BloggingPlatformAPI.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("conversation/{senderId}/{receiverId}")
    public List<Message> getConversation(@PathVariable Integer senderId, @PathVariable Integer receiverId) {
        return messageService.getMessagesBetweenUsers(senderId, receiverId);
    }

    @PostMapping("send")
    public void sendMessage( @RequestParam Integer senderId, @RequestParam Integer receiverId, @RequestParam String content) {
        messageService.sendMessage(senderId, receiverId, content);
    }
}
