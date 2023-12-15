package com.example.BloggingPlatformAPI.controller;

import com.example.BloggingPlatformAPI.model.*;
import com.example.BloggingPlatformAPI.model.dto.SignInInput;
import com.example.BloggingPlatformAPI.model.dto.SignUpOutput;
import com.example.BloggingPlatformAPI.service.AuthenticationService;
import com.example.BloggingPlatformAPI.service.FollowService;
import com.example.BloggingPlatformAPI.service.PostService;
import com.example.BloggingPlatformAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("user/signup")
    public SignUpOutput signUpBlogUser(@RequestBody @Valid User user)
    {
        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInBlogUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.sigInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutBlogUser(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutUser(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }

    @GetMapping("users")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping("follow")
    public String followUser(@RequestBody @Valid Follow follow, @RequestParam String followerEmail, @RequestParam String followerToken)
    {
        if(authenticationService.authenticate(followerEmail,followerToken)) {
            return userService.followUser(follow,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("unfollow/target/{followId}")
    public String unFollowUser(@PathVariable Integer followId, @RequestParam String followerEmail, @RequestParam String followerToken)
    {
        if(authenticationService.authenticate(followerEmail,followerToken)) {
            return userService.unFollowUser(followId,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @PostMapping("post")
    public String createBlogPost(@RequestBody @Valid Post post, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.createBlogPost(post,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("post")
    public String removeBlogPost(@RequestParam Integer postId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlogPost(postId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @PutMapping("user/post/{userId}")
    public Post updateBlogPost(@PathVariable Integer userId, @RequestBody Post updatedPost)
    {
        return userService.updateBlogPost(userId,updatedPost);
    }
    @GetMapping("posts")
    public List<Post> getAllPosts()
    {
        return postService.getAllPosts();
    }

    @GetMapping("public/posts")
    public List<Post> getPublicPosts() {
        return postService.getPublicPosts();
    }
    @PostMapping("like")
    public String addLike(@RequestBody @Valid Like like, @RequestParam String likeEmail, @RequestParam String likerToken)
    {
        if(authenticationService.authenticate(likeEmail,likerToken)) {
            return userService.addLike(like,likeEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @GetMapping("like/count/post/{postId}")
    public String getLikeCountByPost(@PathVariable Integer postId, @RequestParam String userEmail, @RequestParam String userToken)
    {
        if(authenticationService.authenticate(userEmail,userToken)) {
            return userService.getLikeCountByPost(postId,userEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @DeleteMapping("like")
    public String removeBlogPostLike(@RequestParam Integer likeId, @RequestParam String likerEmail, @RequestParam String likerToken)
    {
        if(authenticationService.authenticate(likerEmail,likerToken)) {
            return userService.removeBlogPostLike(likeId,likerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @PostMapping("comment")
    public String addComment(@RequestBody @Valid Comment comment, @RequestParam String commenterEmail, @RequestParam String commenterToken)
    {
        if(authenticationService.authenticate(commenterEmail,commenterToken)) {
            return userService.addComment(comment,commenterEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }


    @DeleteMapping("comment")
    public String removeBlogComment(@RequestParam Integer commentId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlogComment(commentId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @GetMapping("{postId}")
    public Post getPostById(@PathVariable Integer postId)
    {
        return postService.getPostById(postId);
    }
    @PostMapping("{postId}/share")
    public Post sharePost(@PathVariable Integer postId)
    {
        return postService.sharePost(postId);
    }

}
