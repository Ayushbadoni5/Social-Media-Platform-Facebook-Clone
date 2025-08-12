package com.facebook.Controller;

import com.facebook.Dtos.ApiResponse;
import com.facebook.Entities.User;
import com.facebook.Repositories.UserRepository;
import com.facebook.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class LikeController {


    @Autowired
    private LikeService likeService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/like/{postId}")
    public ResponseEntity<ApiResponse> likePost(@PathVariable UUID postId,
                                              Authentication authenticatedUser){

        User user = userRepository.findByEmail(authenticatedUser.getName()).orElseThrow(
                () -> new RuntimeException("User not found"));
        likeService.likePost(authenticatedUser.getName(),postId);

        ApiResponse response = new ApiResponse(
                "Post liked successfully",
                user.getId(), user.getName(),Instant.now()
        );

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("/removelike/{postId}")
    public ResponseEntity<ApiResponse> removeLike(@PathVariable UUID postId, Authentication authenticatedUser){

        User user = userRepository.findByEmail(authenticatedUser.getName()).orElseThrow(
                () -> new RuntimeException("User not found"));
        likeService.removeLike(authenticatedUser.getName(), postId);

        ApiResponse response = new ApiResponse(
                "Like removed successfully from the post",user.getId(), user.getName(),Instant.now()
        );


        return new ResponseEntity<>( response, HttpStatus.OK);
    }

}
