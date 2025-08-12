package com.facebook.Repositories;

import com.facebook.Entities.Like;
import com.facebook.Entities.Post;
import com.facebook.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    Optional<Like> findByUserAndPost(User user, Post post);
    long countByPostId(UUID postId);
}
