package com.facebook.Repositories;

import com.facebook.Entities.Comment;
import com.facebook.Entities.LikeAComment;
import com.facebook.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeACommentRepository extends JpaRepository<LikeAComment, UUID> {
    Optional<LikeAComment> findAllByUserAndComment(User user, Comment comment);

    long countByComment(Comment comment);
}
