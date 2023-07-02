package com.java.springboot.demo.service.impl;

import com.java.springboot.demo.common.UserType;
import com.java.springboot.demo.entity.UserFlag;
import com.java.springboot.demo.exception.UserServiceException;
import com.java.springboot.demo.repository.UserRepository;
import com.java.springboot.demo.service.UserService;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserFlag> processMessageData(String message) {
        log.info("Process Message Data Started...!!!!");
        return Arrays.stream(message.split(","))
                .map(String::trim)
                .map(this::populateUserObject)
                .collect(Collectors.toList());
    }

    private UserFlag populateUserObject(String inputString) {
        UserFlag userObj = new UserFlag();

        UserType userType = Arrays.stream(UserType.values())
                .filter(type -> {
                    String extractedType = extractUserValue(inputString, "-([^:]+):");
                    return extractedType != null && extractedType.equalsIgnoreCase(type.name());
                })
                .findFirst()
                .orElse(null);

        if (userType != null) {
            userObj.setType(userType.getAction());
            log.info("Name: " + userType.name() + ", Type: " + userType.getAction());
        }

        String userID = extractUserValue(inputString, "^(.*?)-");
        String userValue = extractUserValue(inputString, ":(.*)");

        userObj.setId(Long.valueOf(userID));
        userObj.setValue(userValue);

        return userObj;
    }

    private String extractUserValue(String inputString, String inputPattern) {
        Pattern pattern = Pattern.compile(inputPattern);
        Matcher matcher = pattern.matcher(inputString);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    @Override
    public List<UserFlag> createUserFlag(List<UserFlag> userFlagMapList) {
         validateUserFlags(userFlagMapList);
         return userRepository.saveAll(userFlagMapList);
    }

    @Override
    public UserFlag fetchUserById(Long userflagId) {
        Optional<UserFlag> optionalUser = userRepository.findById(userflagId);
        return optionalUser.orElse(null);
    }

     @Override
     public UserFlag fetchByUserIdAndType(Long userFlagId, String userType) {
         return Arrays.stream(UserType.values())
                 .filter(type -> !StringUtils.isBlank(userType) && userType.equals(type.name()))
                 .findFirst()
                 .flatMap(type -> userRepository.findByIdAndType(userFlagId, type.getAction()))
                 .orElse(null);
     }


    @Override
    public UserFlag updateUserFlag(UserFlag userflag) {
        return userRepository.findById(userflag.getId())
                .map(existingUserFlag -> {
                    existingUserFlag.setType(userflag.getType());
                    existingUserFlag.setValue(userflag.getValue());
                    return userRepository.save(existingUserFlag);
                })
                .orElse(null);
    }

    @Override
    public void deleteUser(Long userFlagId) {
        userRepository.deleteById(userFlagId);
    }

    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    // Validation logic to check for null values
    private void validateUserFlags(List<UserFlag> userFlagList) {
        for (UserFlag userFlag : userFlagList) {
            if (userFlag.getId() == null || StringUtils.isBlank(userFlag.getType())|| StringUtils.isBlank(userFlag.getValue())) {
                throw new UserServiceException("UserFlag object must have non-null ID, TYPE, and VALUE");
            }
        }
    }

}
