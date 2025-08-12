package com.facebook.Controller;

import com.facebook.Dtos.UserResponse;
import com.facebook.Dtos.UserUpdate;
import com.facebook.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/myprofile")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authenticatedUser){
        UserResponse response = profileService.getMyProfile(authenticatedUser.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody UserUpdate update, Authentication authenticatedUser){
        UserResponse response = profileService.updateMyProfile(authenticatedUser.getName(), update);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
