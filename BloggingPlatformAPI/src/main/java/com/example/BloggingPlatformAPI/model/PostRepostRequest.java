package com.example.BloggingPlatformAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRepostRequest {
    private Post originalPost;
    private User user;
}
