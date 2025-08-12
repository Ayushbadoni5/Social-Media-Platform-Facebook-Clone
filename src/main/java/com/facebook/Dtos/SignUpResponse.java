package com.facebook.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpResponse {

    private UUID id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    private String gender;

    private String city;

}
