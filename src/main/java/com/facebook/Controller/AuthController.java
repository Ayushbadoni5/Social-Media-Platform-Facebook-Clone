package com.facebook.Controller;


import com.facebook.Dtos.*;
import com.facebook.Entities.User;
import com.facebook.Repositories.UserRepository;
import com.facebook.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp (@RequestBody SignUpRequest request){
        SignUpResponse response = authService.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request){

            SignInResponse response = authService.signIn(request);
            return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse> signout(HttpServletRequest request) {
        User user = authService.signOut(request);

        ApiResponse response = ApiResponse.builder()
                .message("Your account logged out successfully")
                .userId(user.getId())
                .username(user.getName())
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
