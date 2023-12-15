package com.example.BloggingPlatformAPI.service;

import com.example.BloggingPlatformAPI.model.*;
import com.example.BloggingPlatformAPI.model.dto.SignInInput;
import com.example.BloggingPlatformAPI.model.dto.SignUpOutput;
import com.example.BloggingPlatformAPI.repository.IPostRepo;
import com.example.BloggingPlatformAPI.repository.IUserRepo;
import com.example.BloggingPlatformAPI.service.emailUtility.EmailHandler;
import com.example.BloggingPlatformAPI.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    LikeService likeService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    FollowService followService;

    @Autowired
    IPostRepo postRepo;

    public  List<User> getAllUsers() {
        return userRepo.findAll();
    }


    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //saveAppointment the user with the new encrypted password

            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String sigInUser(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }
    public String sigOutUser(String email) {
        User user = userRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User Signed out successfully";
    }

    public String createBlogPost(Post post, String email) {
        User postOwner = userRepo.findFirstByUserEmail(email);
        post.setPostOwner(postOwner);
        return postService.createBlogPost(post, post.isPublic());
    }

    public String removeBlogPost(Integer postId, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return postService.removeBlogPost(postId,user);
    }

    public String addLike(Like like, String likeEmail) {

        Post blogPost = like.getBlogPost();
        boolean postValid = postService.validatePost(blogPost);

        if(postValid) {


            User liker = userRepo.findFirstByUserEmail(likeEmail);
            if(likeService.isLikeAllowedOnThisPost(blogPost,liker))
            {
                like.setLiker(liker);
                return likeService.addLike(like);
            }
            else {
                return "Already Liked!!";
            }

        }
        else {
            return "Cannot like on Invalid Post!!";
        }

    }

    public String getLikeCountByPost(Integer postId, String userEmail) {

        Post validPost = postService.getPostById(postId);

        if(validPost != null)
        {
            Integer likeCountForPost =  likeService.getLikeCountForPost(validPost);
            return String.valueOf(likeCountForPost);
        }
        else {
            return "Cannot like on Invalid Post!!";
        }
    }
    private boolean authorizeLikeRemover(String potentialLikeRemover, Like like) {

        String  likeOwnerEmail = like.getLiker().getUserEmail();
        return potentialLikeRemover.equals(likeOwnerEmail);
    }

    public String removeBlogPostLike(Integer likeId, String likerEmail) {

        Like like  = likeService.findLike(likeId);
        if(like!=null)
        {
            if(authorizeLikeRemover(likerEmail,like))
            {
                likeService.removeLike(like);
                return "like deleted successfully";
            }
            else
            {
                return "Unauthorized delete detected...Not allowed!!!!";
            }

        }
        else
        {
            return "Invalid like";
        }
    }
    public String addComment(Comment comment, String commenterEmail) {
        boolean postValid = postService.validatePost(comment.getBlogPost());
        if(postValid) {
            User commenter = userRepo.findFirstByUserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }
        else {
            return "Cannot comment on Invalid Post!!";
        }
    }
    private boolean authorizeCommentRemover(String email, Comment comment) {
        String  commentOwnerEmail = comment.getCommenter().getUserEmail();
        String  postOwnerEmail  = comment.getBlogPost().getPostOwner().getUserEmail();

        return postOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }
    public String removeBlogComment(Integer commentId, String email) {
        Comment comment  = commentService.findComment(commentId);
        if(comment!=null)
        {
            if(authorizeCommentRemover(email,comment))
            {
                commentService.removeComment(comment);
                return "comment deleted successfully";
            }
            else
            {
                return "Unauthorized delete detected...Not allowed!!!!";
            }

        }
        else
        {
            return "Invalid Comment";
        }
    }

    public String followUser(Follow follow, String followerEmail) {

        User followTargetUser = userRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);

        User follower = userRepo.findFirstByUserEmail(followerEmail);

        if(followTargetUser!=null)
        {
            if(followService.isFollowAllowed(followTargetUser,follower))
            {
                followService.startFollowing(follow,follower);
                return follower.getUserName()  + " is now following " + followTargetUser.getUserName();
            }
            else {
                return follower.getUserName()  + " already follows " + followTargetUser.getUserName();
            }
        }
        else {
            return "User to be followed is Invalid!!!";
        }
    }

    private boolean authorizeUnfollow(String email, Follow follow)
    {
        String  targetEmail = follow.getCurrentUser().getUserEmail();
        String  followerEmail  = follow.getCurrentUserFollower().getUserEmail();

        return targetEmail.equals(email) || followerEmail.equals(email);
    }
    public String unFollowUser(Integer followId, String followerEmail) {

        Follow follow  = followService.findFollow(followId);
        if(follow != null)
        {
            if(authorizeUnfollow(followerEmail,follow))
            {
                followService.unfollow(follow);
                return follow.getCurrentUser().getUserName() + " not followed by " + followerEmail;
            }
            else
            {
                return "Unauthorized unfollow detected...Not allowed!!!!";
            }

        }
        else
        {
            return "Invalid follow mapping";
        }
    }
    public Post updateBlogPost(Integer userId, Post updatedPost) {

        Optional<Post> optionalPost = postRepo.findById(userId);
        if (!optionalPost.isPresent()) {

            throw new IllegalArgumentException("Post not found with ID: " + userId);
        }

        Post existingPost = optionalPost.get();

        existingPost.setPostCaption(updatedPost.getPostCaption());
        existingPost.setPostContent(updatedPost.getPostContent());
        existingPost.setPostType(updatedPost.getPostType());

        return postRepo.save(existingPost);

    }


}
