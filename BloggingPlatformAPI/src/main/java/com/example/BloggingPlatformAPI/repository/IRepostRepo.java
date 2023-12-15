package com.example.BloggingPlatformAPI.repository;

import com.example.BloggingPlatformAPI.model.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepostRepo extends JpaRepository<Repost, Integer> {
    @Query("SELECT r FROM Repost r WHERE r.user.id = :userId AND r.originalPost.id = :postId")
    Repost findByUserAndOriginalPost(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
