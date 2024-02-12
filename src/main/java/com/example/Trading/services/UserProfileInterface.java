package com.example.Trading.services;

import com.example.Trading.entity.UserInfo;
import com.example.Trading.model.NewProfile;
import com.example.Trading.model.ResponseMessage;
import com.example.Trading.model.UpdateProfile;
import org.springframework.http.ResponseEntity;

public interface UserProfileInterface {
    ResponseMessage createNewProfile(NewProfile UserInfo);
    ResponseEntity<UserInfo> updateUserProfileName(UpdateProfile updateProfile, String emailOrMobileNumber);

}
