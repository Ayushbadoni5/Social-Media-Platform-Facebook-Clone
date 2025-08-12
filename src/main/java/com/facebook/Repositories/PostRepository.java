package com.facebook.Repositories;

import com.facebook.Entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findAllByOrderByCreatedOnDesc(Pageable pageable);
    Page<Post> findAllByOrderByCreatedOnAsc(Pageable pageable);


    Page<Post> findAllByOrderByLikeCountDesc(Pageable pageable);
    Page<Post> findAllByOrderByCommentCountDesc(Pageable pageable);
}
