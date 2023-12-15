package com.example.BloggingPlatformAPI.repository;

import com.example.BloggingPlatformAPI.model.AuthenticationToken;
import com.example.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {

    AuthenticationToken findFirstByTokenValue(String authTokenValue);
    AuthenticationToken findFirstByUser(User user);


}
