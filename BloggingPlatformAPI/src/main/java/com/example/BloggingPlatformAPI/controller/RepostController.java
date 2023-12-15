package com.example.BloggingPlatformAPI.controller;

import com.example.BloggingPlatformAPI.model.Post;
import com.example.BloggingPlatformAPI.model.PostRepostRequest;
import com.example.BloggingPlatformAPI.model.Repost;
import com.example.BloggingPlatformAPI.model.User;
import com.example.BloggingPlatformAPI.service.PostService;
import com.example.BloggingPlatformAPI.service.RepostService;
import com.example.BloggingPlatformAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Validated
@RestController
public class RepostController {

    @Autowired
    private RepostService repostService;

    @PostMapping("repost")
    public void repost(@RequestBody @Valid PostRepostRequest request) {
        repostService.repost(request.getOriginalPost(), request.getUser());
    }
}

