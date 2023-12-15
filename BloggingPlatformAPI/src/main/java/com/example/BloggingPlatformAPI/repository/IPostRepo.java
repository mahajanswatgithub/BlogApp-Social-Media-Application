package com.example.BloggingPlatformAPI.repository;

import com.example.BloggingPlatformAPI.model.AuthenticationToken;
import com.example.BloggingPlatformAPI.model.Post;
import com.example.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post,Integer>{
   // List<Post> findByUser(User user);
    List<Post> findByIsPublicTrue();
}
