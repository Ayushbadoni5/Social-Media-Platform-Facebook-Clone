package com.facebook.Dtos;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
public class ErrorResponse {
    private String message;
}
