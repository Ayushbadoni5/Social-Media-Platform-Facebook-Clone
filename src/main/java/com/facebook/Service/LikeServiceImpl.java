package com.facebook.Service;

import com.facebook.Entities.Like;
import com.facebook.Entities.Post;
import com.facebook.Entities.User;
import com.facebook.Repositories.LikeRepository;
import com.facebook.Repositories.PostRepository;
import com.facebook.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public void likePost(String email, UUID postId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new RuntimeException("Post not found"));

        likeRepository.findByUserAndPost(user,post)
                .orElseGet(()->{post.setLikeCount(post.getLikeCount()+1);
                postRepository.save(post);
                return likeRepository.save(
                        Like.builder()
                                .user(user)
                                .post(post)
                                .build());
                });

    }

    @Override
    public void removeLike(String email, UUID postId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new RuntimeException("Post not found"));

        likeRepository.findByUserAndPost(user,post).ifPresent(like -> {
            post.setLikeCount(Math.max(0,post.getLikeCount()-1));
            postRepository.save(post);
            likeRepository.delete(like);
        });
    }

}
