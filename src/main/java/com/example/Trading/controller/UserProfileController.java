package com.example.Trading.controller;

import com.example.Trading.constants.ApiConstants;
import com.example.Trading.entity.UserInfo;
import com.example.Trading.model.NewProfile;
import com.example.Trading.model.ResponseMessage;
import com.example.Trading.model.UpdateProfile;
import com.example.Trading.services.UserProfileInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Autowired
    UserProfileInterface service;

    @PostMapping(ApiConstants.register) //Register or Create new Profile
    public ResponseMessage createNewProfile(@RequestBody @Valid NewProfile newProfile) {
        return service.createNewProfile(newProfile);
    }
    @PostMapping(ApiConstants.UpdateProfile)
    public ResponseEntity<UserInfo> updateProfile(@RequestBody @Valid UpdateProfile updateProfile, @PathVariable String emailOrMobileNumber){
        return service.updateUserProfileName(updateProfile,emailOrMobileNumber);
    }

}
