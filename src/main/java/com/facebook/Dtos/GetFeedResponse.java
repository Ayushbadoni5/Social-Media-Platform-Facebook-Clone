package com.facebook.Dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFeedResponse {
    private UUID id;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    private long likesCount;
    private long commentCount;
    private List<CommentResponse> comments;

}
