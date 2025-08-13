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

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request){

            SignInResponse response = authService.signIn(request);
            return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @PostMapping("/logout")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        User user = authService.signOut(request);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
