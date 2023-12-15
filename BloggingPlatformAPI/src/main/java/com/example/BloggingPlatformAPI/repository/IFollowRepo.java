package com.example.BloggingPlatformAPI.repository;

import com.example.BloggingPlatformAPI.model.Follow;
import com.example.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFollowRepo extends JpaRepository<Follow,Integer> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User targetUser, User follower);
}
