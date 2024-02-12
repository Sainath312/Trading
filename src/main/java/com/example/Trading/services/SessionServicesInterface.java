package com.example.Trading.services;

import com.example.Trading.model.*;

public interface SessionServicesInterface {


    ResponseMessage loginUser(AuthRequest request) ;


    ResponseMessage logoutUser(AuthRequest request);



}
