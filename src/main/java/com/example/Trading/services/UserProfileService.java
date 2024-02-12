package com.example.Trading.services;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.entity.UserInfo;
import com.example.Trading.exceptions.AlreadyExists;
import com.example.Trading.exceptions.UserNotExists;
import com.example.Trading.model.NewProfile;
import com.example.Trading.model.ResponseMessage;
import com.example.Trading.model.UpdateProfile;
import com.example.Trading.repository.UserInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserProfileService implements UserProfileInterface{

    public static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    PasswordEncoder passwordEncoder;


    //Method For Register New User(create new Login)
    public ResponseMessage createNewProfile(NewProfile userInfo) {
        try {
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            userInfo.setStatus("Created");
            UserInfo profile = new UserInfo(userInfo.getUserName(), userInfo.getEmail(), userInfo.getMobileNumber(), userInfo.getPassword(),userInfo.getStatus());
            profile.setRoles(profile.getRoles());
            userInfoRepo.save(profile);
            logger.info("New Profile Created Successfully With Name" + userInfo.getUserName());
            return new ResponseMessage(StringConstants.Created);
        } catch (Exception e) {
            logger.warn("User is already is existing in database with name  " + userInfo.getUserName());
            throw new AlreadyExists(StringConstants.UserExists);
        }
    }
    public ResponseEntity<UserInfo> updateUserProfileName(UpdateProfile userInfo, String emailOrUserName) {

        UserInfo user = userInfoRepo.findByEmail(emailOrUserName).orElse(userInfoRepo.findByUserName(emailOrUserName).orElseThrow(() -> new UserNotExists(StringConstants.User_Not_Exists)));
        logger.info("Trade Id : " + user.getUserName() + " is Found");
        user.setMobileNumber(user.getMobileNumber());
        user.setStatus("Updated");
        UserInfo updatedTrade = userInfoRepo.save(user);
        logger.info("UserProfile Is Updated");

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-type", "application/json")
                .body(updatedTrade);

    }


}
