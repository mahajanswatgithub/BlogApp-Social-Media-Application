package com.example.BloggingPlatformAPI.repository;

import com.example.BloggingPlatformAPI.model.Like;
import com.example.BloggingPlatformAPI.model.Post;
import com.example.BloggingPlatformAPI.model.User;
import org.hibernate.query.criteria.JpaFetchParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ILikeRepo extends JpaRepository<Like, Integer> {
    List<Like> findByBlogPostAndLiker(Post blogPost, User liker);

    List<Like> findByBlogPost(Post validPost);
}
