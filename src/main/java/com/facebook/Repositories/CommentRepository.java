package com.facebook.Repositories;

import com.facebook.Entities.Comment;
import com.facebook.Entities.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByPostOrderByCreatedAtDesc(@Param("post")Post post, Pageable pageable);

    long countByPostId(UUID postId);

    List<Comment> findTopCommentsByPostOrderByLikesDesc(@Param("post")Post post, Pageable pageable);

}
