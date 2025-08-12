package com.facebook.Service;

import com.facebook.Dtos.UserResponse;
import com.facebook.Dtos.UserUpdate;

public interface ProfileService {
    UserResponse getMyProfile(String email);
    UserResponse updateMyProfile(String email, UserUpdate update);
}
