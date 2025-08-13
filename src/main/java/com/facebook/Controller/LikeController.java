package com.facebook.Controller;

import com.facebook.Repositories.UserRepository;
import com.facebook.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class LikeController {


    @Autowired
    private LikeService likeService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> likePost(@PathVariable UUID postId,
                                         Authentication authenticatedUser){
        likeService.likePost(authenticatedUser.getName(),postId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Void> removeLike(@PathVariable UUID postId, Authentication authenticatedUser){


        likeService.removeLike(authenticatedUser.getName(), postId);


        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
