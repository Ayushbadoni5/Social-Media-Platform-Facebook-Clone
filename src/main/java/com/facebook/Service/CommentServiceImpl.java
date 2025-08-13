package com.facebook.Service;

import com.facebook.Dtos.CommentRequest;
import com.facebook.Dtos.CommentResponse;
import com.facebook.Entities.Comment;
import com.facebook.Entities.Post;
import com.facebook.Entities.User;
import com.facebook.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;


    @Override
    public CommentResponse addComment(String email, UUID postId, CommentRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not found"));


        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .likes(0)
                .build();

        Comment savedComment = commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount()+1);
        postRepository.save(post);

        return CommentResponse.builder()
                .id(savedComment.getId())
                .content(savedComment.getContent())
                .commenterName(savedComment.getUser().getName())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }

    @Override
    public void deleteComment(String email, UUID commentId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new RuntimeException("Comment not found"));

        if (!comment.getUser().getEmail().equals(user.getEmail())){
            throw new RuntimeException("You can delete only your own comment");
        }

        Post post = comment.getPost();
        post.setCommentCount(Math.max(0, post.getCommentCount()-1));
        postRepository.save(post);

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentResponse> getCommentsForPost(UUID postId, int page, int size) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not found"));

        Pageable pageable = PageRequest.of(page,size);

        List<Comment> comments = commentRepository.findByPostOrderByCreatedAtDesc(post, pageable);

        return comments.stream()
                .map(comm -> CommentResponse.builder()
                        .id(comm.getId())
                        .content(comm.getContent())
                        .commenterName(comm.getUser().getName())
                        .createdAt(comm.getCreatedAt())
                        .likesCount(commentLikeRepository.countByComment(comm))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getTopCommentForPost(UUID postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        Pageable topTwo = PageRequest.of(0,2);
        List<Comment> comments = commentRepository.findTopCommentsByPostOrderByLikesDesc(post, topTwo);

        return comments.stream()
                .map(comm -> CommentResponse.builder()
                        .id(comm.getId())
                        .likesCount(commentLikeRepository.countByComment(comm))
                        .content(comm.getContent())
                        .createdAt(comm.getCreatedAt())
                        .commenterName(comm.getUser().getName())
                        .build())
                .toList();
    }
}
