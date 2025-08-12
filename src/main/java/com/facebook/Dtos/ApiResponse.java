package com.facebook.Dtos;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private UUID userId;
    private String username;
    private Instant timestamp;
}
