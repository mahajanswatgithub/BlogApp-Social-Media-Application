package com.example.BloggingPlatformAPI.repository;

import com.example.BloggingPlatformAPI.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepo extends JpaRepository<Message,Integer> {
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :senderId AND m.receiver.id = :receiverId) OR " +
            "(m.sender.id = :receiverId AND m.receiver.id = :senderId) " +
            "ORDER BY m.sentAt ASC")
    List<Message> findChatMessages(@Param("senderId") Integer senderId, @Param("receiverId") Integer receiverId);
}
