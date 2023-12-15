package com.example.BloggingPlatformAPI.service;
import com.example.BloggingPlatformAPI.model.Message;
import com.example.BloggingPlatformAPI.model.User;
import com.example.BloggingPlatformAPI.repository.IMessageRepo;
import com.example.BloggingPlatformAPI.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private IMessageRepo messageRepo;
    @Autowired
    private IUserRepo userRepo;
    public List<Message> getMessagesBetweenUsers(Integer senderId, Integer receiverId) {

         return messageRepo.findChatMessages(senderId, receiverId);
    }

    public void sendMessage(Integer senderId, Integer receiverId, String content) {

         User sender = userRepo.findById(senderId).orElse(null);
         User receiver = userRepo.findById(receiverId).orElse(null);
         if (sender != null && receiver != null) {
             Message message = new Message();
             message.setSender(sender);
             message.setReceiver(receiver);
             message.setContent(content);
             message.setSentAt(LocalDateTime.now());
             messageRepo.save(message);
             System.out.println("Message Sent Successfully!!!!!");
         }
    }
}
