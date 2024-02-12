package com.example.Trading.repository;


import com.example.Trading.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserName(String userName);
    Optional<UserInfo> findByEmail(String email);



}