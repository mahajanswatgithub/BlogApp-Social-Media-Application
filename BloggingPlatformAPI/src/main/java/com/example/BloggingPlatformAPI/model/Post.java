package com.example.BloggingPlatformAPI.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class,scope=Post.class,property="postId")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer PostId;
    private String postCaption;
    private String postContent;
    private boolean isPublic;
    @Enumerated(EnumType.STRING)
    private PostType postType;

    private boolean postShared;
    @ManyToOne
    @JoinColumn(name = "fk_post_user_id")
    private User postOwner;

    @OneToMany(mappedBy = "originalPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Repost> reposts = new ArrayList<>();
    public List<Repost> getReposts() {
        return reposts;
    }
}
