package com.example.Trading.services;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.entity.*;
import com.example.Trading.exceptions.*;
import com.example.Trading.model.*;
import com.example.Trading.repository.*;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Log4j2
@Service
@Transactional
@Component
public class SessionServices implements SessionServicesInterface {

    public static final Logger logger = LoggerFactory.getLogger(SessionServices.class);

    @Autowired
    JwtService jwtService;
    @Autowired
    UserInfoRepo userInfoRepo;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SessionRepo sessionRepo;

    Session sessionTokenService;


    // Authenticating the User (using userName(or)userEmail and Password)
    // Authenticating the User (using userName(or)userEmail and Password)
    public boolean authenticateUser(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            // Check whether the user credentials are present in the database and authenticate
            if (authentication.isAuthenticated()) {
                String username = authRequest.getUsername();
                Long id = getUserIdByUsernameOrEmail(username);
                sessionRepo.findByUserId(id);
                return true;
            }
        } catch (AuthenticationException e) {
            throw new UserNotExists(StringConstants.User_Not_Exists);
        }
        return false;
    }

    public ResponseMessage loginUser(AuthRequest authRequest) {
        if (authenticateUser(authRequest)) {
            String username = authRequest.getUsername();
            Long id = getUserIdByUsernameOrEmail(username);
            Session userSession = sessionRepo.findByUserId(id);

            if (userSession == null) {
                // Generate a session token and store it
                String token = jwtService.generateToken(username);
                Session newUserSession = new Session();
                newUserSession.setUserId(id);
                newUserSession.setToken(token);
                sessionRepo.save(newUserSession);

                // Store the session token in your custom session token service

                return new ResponseMessage(token);
            } else {
                throw new AlreadyExists(StringConstants.UserExists);
            }
        } else {
            throw new UserNotExists(StringConstants.User_Not_Exists);
        }
    }

    public ResponseMessage logoutUser(AuthRequest authRequest) {
        if (authenticateUser(authRequest)) {
            String username = authRequest.getUsername();
            Long id = getUserIdByUsernameOrEmail(username);
            Session userSession = sessionRepo.findByUserId(id);

            if (userSession != null) {
                // Delete the session from the database
                logger.info("Returned by Session method : User LogOut Successfully");
                sessionRepo.delete(userSession);
                return new ResponseMessage(StringConstants.LogOut);

            } else {
                logger.warn("Returned by Session Service method : Failed To LogOut");
                throw new UserNotExists(StringConstants.NotLogin);
            }
        } else {
            throw new UserNotExists(StringConstants.User_Not_Exists);
        }
    }
    public Long getUserIdByUsernameOrEmail(String usernameOrEmail) {
        // Check if the input is a valid email address
        if (isValidEmail(usernameOrEmail)) {
            return userInfoRepo.findByEmail(usernameOrEmail)
                    .map(UserInfo::getUserId)
                    .orElse(null);
        } else {
            // Assuming username is unique, directly search by username
            return userInfoRepo.findByUserName(usernameOrEmail)
                    .map(UserInfo::getUserId)
                    .orElse(null);
        }
    }
    private boolean isValidEmail(String email) {
        // Define a simple regex pattern for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

