package com.facebook.Service;

import java.util.UUID;

public interface LikeService {
    void likePost(String email, UUID postId);
    void removeLike(String email, UUID postId);
}