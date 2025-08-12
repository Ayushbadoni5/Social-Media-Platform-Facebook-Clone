package com.facebook.Controller;


import com.facebook.Dtos.ApiResponse;
import com.facebook.Entities.User;
import com.facebook.Repositories.UserRepository;
import com.facebook.Service.LikeACommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class LikeACommentController {
    private final LikeACommentService likeACommentService;
    private final UserRepository userRepository;

    @PostMapping("/like/{commentId}")
    public ResponseEntity<ApiResponse> likeComment(@PathVariable UUID commentId, Authentication authenticatedUser){

        User user = userRepository.findByEmail(authenticatedUser.getName())
                .orElseThrow(()-> new RuntimeException("User Not found"));

        likeACommentService.likeComment(commentId,authenticatedUser.getName());

        ApiResponse response = new ApiResponse("Comment Liked Successfully",
                user.getId(),
                user.getName(),
                Instant.now());

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/removeLike/{commentId}")
    public ResponseEntity<ApiResponse> removeLikeFromComment(@PathVariable UUID commentId, Authentication authenticatedUser){

        User user = userRepository.findByEmail(authenticatedUser.getName())
                .orElseThrow(()-> new RuntimeException("User Not found"));

        likeACommentService.removeLike(commentId,authenticatedUser.getName());

        ApiResponse response = new ApiResponse("Like removed from comment successfully",
                user.getId(),
                user.getName(),
                Instant.now());

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


}
