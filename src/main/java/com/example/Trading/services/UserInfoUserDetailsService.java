package com.example.Trading.services;

import com.example.Trading.config.UserInfoUserDetails;
import com.example.Trading.entity.UserInfo;
import com.example.Trading.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    UserInfoRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userInfoByUserName = repository.findByUserName(username);
        if (userInfoByUserName.isPresent()) {
            return new UserInfoUserDetails(userInfoByUserName.get());
        }
        Optional<UserInfo> userInfoByEmail = repository.findByEmail(username);
        return userInfoByEmail.map(userInfo -> new UserInfoUserDetails(userInfo, true))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Username or mobile number: " + username));
    }


}


