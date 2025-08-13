package com.facebook.Controller;

import com.facebook.Dtos.CommentRequest;
import com.facebook.Dtos.CommentResponse;
import com.facebook.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts/comments/")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable UUID postId,
                                                      @RequestBody CommentRequest request,
                                                      Authentication authenticatedUser){
        CommentResponse response = commentService.addComment(authenticatedUser.getName(),postId,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId,
                                              Authentication authenticatedUser){

        commentService.deleteComment(authenticatedUser.getName(),commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable UUID postId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        List<CommentResponse> comments = commentService.getCommentsForPost(postId, page, size);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }
}
