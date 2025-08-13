package com.facebook.Service;

import com.facebook.Dtos.GetFeedResponse;
import com.facebook.Dtos.PostRequest;
import com.facebook.Dtos.PostResponse;
import com.facebook.Entities.Post;
import com.facebook.Entities.User;
import com.facebook.Enums.PostSortType;
import com.facebook.Repositories.CommentRepository;
import com.facebook.Repositories.LikeRepository;
import com.facebook.Repositories.PostRepository;
import com.facebook.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final CommentService commentService;

    @Override
    public PostResponse createPost(String email, PostRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Post post = Post.builder()
                .content(request.getContent())
                .user(user)
                .build();

        Post saved = postRepository.save(post);

        return PostResponse.builder()
                .id(saved.getId())
                .content(saved.getContent())
                .username(saved.getUser().getName())
                .createdAt(saved.getCreatedOn())
                .build();
    }


    @Override
    public void deletePost(String email, UUID postId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));


        if (!post.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not the owner of this post");
        }


        postRepository.delete(post);
    }

    @Override
    public Map<String, Object> getFeed(PostSortType sortBy, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Post> posts;

        switch (sortBy){
            case NEWEST -> posts = postRepository.findAllByOrderByCreatedOnDesc(pageable);
            case OLDEST -> posts = postRepository.findAllByOrderByCreatedOnAsc(pageable);
            case LIKES -> posts = postRepository.findAllByOrderByLikeCountDesc(pageable);
            case COMMENTS -> posts = postRepository.findAllByOrderByCommentCountDesc(pageable);
            default -> throw new RuntimeException("Invalid sort type");
        }

        List<GetFeedResponse> feedResponses = posts.getContent()
                .stream().map(this::mapToFeedDto).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("posts", feedResponses);
        response.put("currentPage", posts.getNumber());
        response.put("totalPages", posts.getTotalPages());
        response.put("totalItems", posts.getTotalElements());
        response.put("pageSize", posts.getSize());

        return response;
    }

    private GetFeedResponse mapToFeedDto(Post post) {


        long likeCount = likeRepository.countByPostId(post.getId());

        long commentCount = commentRepository.countByPostId(post.getId());

        return GetFeedResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .username(post.getUser().getName())
                .createdAt(post.getCreatedOn())
                .likesCount(likeCount)
                .commentCount(commentCount)
                .comments(commentService.getTopCommentForPost(post.getId()))
                .build();
    }
}
