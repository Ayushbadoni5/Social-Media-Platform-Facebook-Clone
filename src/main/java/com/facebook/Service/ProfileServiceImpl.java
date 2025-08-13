package com.facebook.Service;

import com.facebook.Dtos.UserResponse;
import com.facebook.Dtos.UserUpdate;
import com.facebook.Entities.User;
import com.facebook.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse getMyProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth().toString())
                .gender(user.getGender())
                .city(user.getCity())
                .bio(user.getBio())
                .build();
    }

    @Override
    public UserResponse updateMyProfile(String email, UserUpdate update) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if (update.getBio() != null) {
            user.setBio(update.getBio());
        }

        if (update.getName() != null) {
            user.setName(update.getName());
        }

        if (update.getCity() != null) {
            user.setCity(update.getCity());
        }

        userRepository.save(user);

        return UserResponse.builder()
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth().toString())
                .gender(user.getGender())
                .name(user.getName())
                .city(user.getCity())
                .bio(user.getBio())
                .build();
    }
}
