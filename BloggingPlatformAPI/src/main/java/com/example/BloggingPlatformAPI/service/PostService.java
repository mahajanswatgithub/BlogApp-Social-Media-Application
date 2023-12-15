package com.example.BloggingPlatformAPI.service;

import com.example.BloggingPlatformAPI.model.Post;
import com.example.BloggingPlatformAPI.model.User;
import com.example.BloggingPlatformAPI.repository.IPostRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    IPostRepo postRepo;

    public String createBlogPost(Post post,boolean isPublic) {
        post.setPublic(isPublic);
        postRepo.save(post);
        return "Post uploaded!!!!";
    }

    public String removeBlogPost(Integer postId, User user) {
        Post post  = postRepo.findById(postId).orElse(null);
        if(post != null && post.getPostOwner().equals(user))
        {
            postRepo.deleteById(postId);
            return "Removed successfully";
        }
        else if (post == null)
        {
            return "Post to be deleted does not exist";
        }
        else{
            return "Un-Authorized delete detected....Not allowed";
        }
    }

    public boolean validatePost(Post blogPost) {
        return (blogPost!=null && postRepo.existsById(blogPost.getPostId()));
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    public List<Post> getPublicPosts() {
        return postRepo.findByIsPublicTrue();
    }


    public Post getPostById(Integer id) {
        return postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    public Post sharePost(Integer postId) {
        Post postToShare = getPostById(postId);
        postToShare.setPostShared(true);
        return postRepo.save(postToShare);
    }
}
