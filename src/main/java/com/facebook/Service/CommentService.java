package com.facebook.Service;

import com.facebook.Dtos.CommentRequest;
import com.facebook.Dtos.CommentResponse;

import java.util.List;
import java.util.UUID;


public interface CommentService {
    CommentResponse addComment(String email, UUID postId, CommentRequest request);
    void deleteComment(String email, UUID commentId);
    List<CommentResponse> getCommentsForPost(UUID postId, int page, int size);
    List<CommentResponse> getTopCommentForPost(UUID postId);

}
