package com.facebook.Service;

import com.facebook.Entities.Comment;
import com.facebook.Entities.LikeAComment;
import com.facebook.Entities.User;
import com.facebook.Repositories.CommentLikeRepository;
import com.facebook.Repositories.CommentRepository;
import com.facebook.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public void likeComment(UUID commentId, String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment not found"));

        boolean alreadyLiked = commentLikeRepository.findAllByUserAndComment(user,comment).isPresent();

        if (alreadyLiked){
            throw new RuntimeException("You already liked this comment");
        }

        LikeAComment like = LikeAComment.builder()
                .user(user)
                .comment(comment)
                .likedAt(Instant.now())
                .build();

        commentLikeRepository.save(like);

        comment.setLikes(comment.getLikes()+1);
        commentRepository.save(comment);
    }

    public void removeLike(UUID commentId,String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment not found"));

        LikeAComment likeAComment = commentLikeRepository.findAllByUserAndComment(user, comment)
                .orElseThrow(() -> new RuntimeException("You haven't liked this comment"));

        commentLikeRepository.delete(likeAComment);

        if (comment.getLikes()>0){
            comment.setLikes(comment.getLikes()-1);
            commentRepository.save(comment);
        }
    }
}
