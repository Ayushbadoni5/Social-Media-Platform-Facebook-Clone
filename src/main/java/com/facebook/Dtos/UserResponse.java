package com.facebook.Dtos;

import com.facebook.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String dateOfBirth;
    private Gender gender;
    private String city;
    private String bio;
}
