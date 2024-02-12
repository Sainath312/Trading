package com.example.Trading.controller;

import com.example.Trading.constants.ApiConstants;
import com.example.Trading.model.*;
import com.example.Trading.services.SessionServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SessionController {

    @Autowired
    SessionServicesInterface serviceIntern;


    @PostMapping(ApiConstants.userLogin)           // create user session and get token
    public ResponseMessage authenticateAndGetToken(@RequestBody AuthRequest request) {
        return serviceIntern.loginUser(request);
    }


    @PostMapping(ApiConstants.logout) // user session is invalidated
    public ResponseMessage userLogout(@RequestBody AuthRequest request) {
      return serviceIntern.logoutUser(request);
    }

}

