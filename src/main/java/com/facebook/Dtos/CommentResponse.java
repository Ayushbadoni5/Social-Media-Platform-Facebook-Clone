package com.facebook.Dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private UUID id;
    private String content;
    private String commenterName;
    private long likesCount;
    private LocalDateTime createdAt;
}
