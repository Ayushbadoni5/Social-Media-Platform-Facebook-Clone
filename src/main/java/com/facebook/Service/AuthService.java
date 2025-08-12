package com.facebook.Service;

import com.facebook.Config.JWTTokenHelper;
import com.facebook.Dtos.SignInRequest;
import com.facebook.Dtos.SignInResponse;
import com.facebook.Dtos.SignUpRequest;
import com.facebook.Dtos.SignUpResponse;
import com.facebook.Entities.SignOut;
import com.facebook.Entities.User;
import com.facebook.Enums.Gender;
import com.facebook.Repositories.SignOutRepository;
import com.facebook.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenHelper jwtTokenHelper;
    private final SignOutRepository signOutRepository;

    public SignUpResponse signUp(SignUpRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already Registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .gender(Gender.fromString(request.getGender()))
                .city(request.getCity())
                .enabled(true)
                .build();

        userRepository.save(user);

        return  SignUpResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender().toString())
                .city(user.getCity())
                .build();
    }

    public SignInResponse signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtTokenHelper.generateToken(user);

        return SignInResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build();
    }

    public User signOut(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }

        String token = header.substring(7);

        String email = jwtTokenHelper.getUsernameFromToken(token);

        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

        SignOut loggedOutToken = SignOut.builder()
                    .token(token)
                    .blacklistedAt(Instant.now())
                    .build();

        signOutRepository.save(loggedOutToken);

        return user;
        }
    }

