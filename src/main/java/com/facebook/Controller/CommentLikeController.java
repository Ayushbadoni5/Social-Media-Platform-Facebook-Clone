package com.facebook.Controller;


import com.facebook.Service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;


    @PostMapping("/{commentId}/likes")
    public ResponseEntity<Void> likeComment(@PathVariable UUID commentId, Authentication authenticatedUser){
        commentLikeService.likeComment(commentId,authenticatedUser.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<Void> removeLikeFromComment(@PathVariable UUID commentId, Authentication authenticatedUser){
        commentLikeService.removeLike(commentId,authenticatedUser.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
