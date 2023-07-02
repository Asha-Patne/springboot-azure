package com.java.springboot.demo.service;

import com.java.springboot.demo.entity.UserFlag;

import java.util.List;

public interface UserService {

    List<UserFlag> processMessageData(String message);

    List<UserFlag> createUserFlag(List<UserFlag> userFlagList);

    UserFlag fetchUserById(Long userFlagId);

    UserFlag updateUserFlag(UserFlag userFlag);

    UserFlag fetchByUserIdAndType(Long userFlagId,String userType);

    void deleteUser(Long userId);

    void deleteAllUser();
}
