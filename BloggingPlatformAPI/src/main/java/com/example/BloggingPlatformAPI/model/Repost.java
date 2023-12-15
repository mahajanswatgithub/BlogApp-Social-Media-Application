package com.example.BloggingPlatformAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Repost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_repost_post_id")
    private Post originalPost;

}
