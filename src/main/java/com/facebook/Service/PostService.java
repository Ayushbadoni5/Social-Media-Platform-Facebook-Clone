package com.facebook.Service;

import com.facebook.Dtos.PostRequest;
import com.facebook.Dtos.PostResponse;
import com.facebook.Enums.PostSortType;

import java.util.Map;
import java.util.UUID;

public interface PostService {
    PostResponse createPost(String email, PostRequest request);
    
    void deletePost(String email, UUID postId);

    Map<String, Object> getFeed(PostSortType sortBy, int page, int size);
}
