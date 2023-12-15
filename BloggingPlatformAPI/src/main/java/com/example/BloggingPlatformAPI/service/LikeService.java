package com.example.BloggingPlatformAPI.service;

import com.example.BloggingPlatformAPI.model.Like;
import com.example.BloggingPlatformAPI.model.Post;
import com.example.BloggingPlatformAPI.model.User;
import com.example.BloggingPlatformAPI.repository.ILikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    ILikeRepo likeRepo;

    public String addLike(Like like) {

        likeRepo.save(like);
        return "Insta post liked successfully!!!";

    }


    public boolean isLikeAllowedOnThisPost(Post blogPost, User liker) {
        List<Like> likeList = likeRepo.findByBlogPostAndLiker(blogPost,liker);
        return likeList!=null && likeList.isEmpty();
    }

    public Like findLike(Integer likeId) {
        return likeRepo.findById(likeId).orElse(null);
    }

    public void removeLike(Like like) {
        likeRepo.delete(like);
    }

    public Integer getLikeCountForPost(Post validPost) {
        return likeRepo.findByBlogPost(validPost).size();
    }
}
