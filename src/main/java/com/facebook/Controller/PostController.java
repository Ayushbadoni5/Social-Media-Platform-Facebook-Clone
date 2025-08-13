package com.facebook.Controller;

import com.facebook.Dtos.PostRequest;
import com.facebook.Dtos.PostResponse;
import com.facebook.Enums.PostSortType;
import com.facebook.Repositories.UserRepository;
import com.facebook.Service.CommentService;
import com.facebook.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request, Authentication authenticatedUser){
        PostResponse response = postService.createPost(authenticatedUser.getName(),request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/feed")
    public ResponseEntity<Map<String,Object>> getAllPosts(@RequestParam(defaultValue = "NEWEST") PostSortType sortBy,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        Map<String,Object> response = postService.getFeed(sortBy,page,size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId, Authentication authenticatedUser){

        postService.deletePost(authenticatedUser.getName(),postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
