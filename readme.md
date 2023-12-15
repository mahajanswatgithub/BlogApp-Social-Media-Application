### Frameworks and language used 
* SpringBoot Framework 
* java 

### Data Flow
* Controller - RestController
 
  Used with @GetMapping, @PostMapping,@DeleteMapping, @RequestBody,@PathVaraiable,@Validated,@RequestParam,@Valid and @Autowired which is linked with business logic in service class.

* Services

  1. UserService

     i. SignUp User

     ii. SignIn User

     iii. SigOut User

     iv. Create Blog Post

     v. Remove Blog Post

     Vi. Add Like
     
     Vii. Like CountBy Post

     viii. Authorize Like Remover

     ix. Remove BlogPost Like

     x. Add Comment

     xi. Authorize Comment Remover

     xii. Remove Blog Comment

     xiii. Follow User
     
     xiv. UnFollow User

     xv. Get All Posts of user

     xvi. Get All Users


  2. PostService

     i. Create Blog Post

     ii. Remove Blog Post

     iii. sharePost

     iv. Validate Post

     v. Get Post By Id

     vi. Get All Posts
     
     vii. Get Public Posts

  3. RepostService

     i. Repost

  4. FollowService

     i. Start Following

     ii. Is Follow Allowed
     
     iii. Find Follow
     
     iv. Unfollow

  5. LikeService

     i. Add Like
     
     ii. isLikeAllowedOnThisPost

     iii. Find Like
     
     iv. getLikeCountForPost

     v. Remove Like

  6. CommentService

     i. Add Comment

     ii. Find Comment

     iii. Remove Comment

  7. AuthenticationService

     i. Authenticate

     ii. Save AuthToken

     iii. Find First By User

     iv. Remove Token

  8. MessageService
     
     i. Get Messages Between Users

     ii. send Message
     
* Repository

  JpaRepository

### DataBase Design
![ER-BloggingAPI-DFD](DFD.png)

### Data Structure used in your project

* Arraylist

### Database used in your project
 
* MYSQL-database

### Project Summary

* This is "Blogging Platform API Backend" Application which follows MVC-architecture.I have created this project by using spring initilizer by taking 7 dependency i.e., lombok, spring web, Mysql, JPA, Email, swagger,and Validation. In my model package I have User, Authentication, Follow, Post, Like, Comment, Repost, PostRepostRequest, Message class which has all its related data.
* We can signUp and signIn in this Blog App for creation of account.
* We can Follow or Unfollow to a particular User.
* Posting of post is also possible by this application.
* We can like,comment, and Share a particular Post of User.
* We can Repost a single post multiple time by multiple users 
 (At a time one user can repost one post, he could not able to repost a reposted post again.)
* We can update the Post Information of user using userId.
* We can also Remove posts, likes and comments of a post owner's post.
* We can send messages to other users in this application.
* We can fetch the public posts data.
* If we want to deactivate this account for particular timeline then we can able to sign out from this application as well.

