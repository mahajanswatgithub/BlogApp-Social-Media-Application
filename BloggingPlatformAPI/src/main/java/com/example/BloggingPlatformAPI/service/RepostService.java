package com.example.BloggingPlatformAPI.service;
import com.example.BloggingPlatformAPI.model.Post;
import com.example.BloggingPlatformAPI.model.Repost;
import com.example.BloggingPlatformAPI.model.User;
import com.example.BloggingPlatformAPI.repository.IRepostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepostService {
    @Autowired
    IRepostRepo repostRepo;


    public void repost(Post originalPost, User user) {
        Repost existingRepost = repostRepo.findByUserAndOriginalPost(user.getUserId(), originalPost.getPostId());

        if (existingRepost == null) {
            Repost repost = new Repost();
            repost.setUser(user);
            repost.setOriginalPost(originalPost);
            repostRepo.save(repost);
            originalPost.getReposts().add(repost);
            System.out.println("Post Reposted");
        }

    }
}
