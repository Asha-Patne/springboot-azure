package com.java.springboot.demo.service.impl;

import com.java.springboot.demo.common.UserType;
import com.java.springboot.demo.entity.UserFlag;
import com.java.springboot.demo.exception.UserServiceException;
import com.java.springboot.demo.repository.UserRepository;
import com.java.springboot.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


import com.java.springboot.demo.entity.UserFlag;
import com.java.springboot.demo.exception.UserServiceException;
import com.java.springboot.demo.repository.UserRepository;
import com.java.springboot.demo.service.UserService;
import com.java.springboot.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
      //  MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessMessageData() {
        String message = "1-Admin:Value1, 2-User:Value2";
        List<UserFlag> expectedUserFlags = Arrays.asList(
                new UserFlag(1L, "Admin", "Value1"),
                new UserFlag(2L, "User", "Value2")
        );

        List<UserFlag> userFlags = userService.processMessageData(message);

     //   Assertions.assertEquals(expectedUserFlags, userFlags);
    }

    @Test
    public void testCreateUserFlag() {
        List<UserFlag> userFlags = Arrays.asList(
                new UserFlag(1L, "Admin", "Value1"),
                new UserFlag(2L, "User", "Value2")
        );

        when(userRepository.saveAll(userFlags)).thenReturn(userFlags);

        List<UserFlag> savedUserFlags = userService.createUserFlag(userFlags);

        Assertions.assertEquals(userFlags, savedUserFlags);
        verify(userRepository, times(1)).saveAll(userFlags);
    }

    @Test
    public void testFetchUserById() {
        Long userFlagId = 1L;
        UserFlag expectedUserFlag = new UserFlag(1L, "Admin", "Value1");

        when(userRepository.findById(userFlagId)).thenReturn(Optional.of(expectedUserFlag));

        UserFlag userFlag = userService.fetchUserById(userFlagId);

        Assertions.assertEquals(expectedUserFlag, userFlag);
        verify(userRepository, times(1)).findById(userFlagId);
    }

    @Test
    public void testFetchByUserIdAndType() {
        Long userFlagId = 1L;
        String userType = "Admin";
        UserFlag expectedUserFlag = new UserFlag(1L, "Admin", "Value1");

        when(userRepository.findByIdAndType(userFlagId, userType)).thenReturn(Optional.of(expectedUserFlag));

        UserFlag userFlag = userService.fetchByUserIdAndType(userFlagId, userType);

       // Assertions.assertEquals(expectedUserFlag, userFlag);
       // verify(userRepository, times(1)).findByIdAndType(userFlagId, userType);
    }

    @Test
    public void testUpdateUserFlag() {
        UserFlag userFlag = new UserFlag(1L, "Admin", "Value1");
        UserFlag updatedUserFlag = new UserFlag(1L, "User", "Value2");

        when(userRepository.findById(userFlag.getId())).thenReturn(Optional.of(userFlag));
        when(userRepository.save(userFlag)).thenReturn(updatedUserFlag);

        UserFlag result = userService.updateUserFlag(userFlag);

        Assertions.assertEquals(updatedUserFlag, result);
        verify(userRepository, times(1)).findById(userFlag.getId());
        verify(userRepository, times(1)).save(userFlag);
    }

    @Test
    public void testDeleteUser() {
        Long userFlagId = 1L;

        userService.deleteUser(userFlagId);

        verify(userRepository, times(1)).deleteById(userFlagId);
    }

    @Test
    public void testDeleteAllUser() {
        userService.deleteAllUser();

        verify(userRepository, times(1)).deleteAll();
    }

  /*  @Test
    public void testValidateUserFlags_AllValid() {
        List<UserFlag> userFlags = Arrays.asList(
                new UserFlag(1L, "Admin", "Value1"),
                new UserFlag(2L, "User", "Value2")
        );

        Assertions.assertDoesNotThrow(() -> userService.validateUserFlags(userFlags));
    }*/

    /*@Test
    public void testValidateUserFlags_NullId() {
        List<UserFlag> userFlags = Arrays.asList(
                new UserFlag(null, "Admin", "Value1"),
                new UserFlag(2L, "User", "Value2")
        );

        Assertions.assertThrows(UserServiceException.class, () -> userService.validateUserFlags(userFlags));
    }*/

    /*@Test
    public void testValidateUserFlags_NullType() {
        List<UserFlag> userFlags = Arrays.asList(
                new UserFlag(1L, null, "Value1"),
                new UserFlag(2L, "User", "Value2")
        );

        Assertions.assertThrows(UserServiceException.class, () -> userService.validateUserFlags(userFlags));
    }*/

   /* @Test
    public void testValidateUserFlags_NullValue() {
        List<UserFlag> userFlags = Arrays.asList(
                new UserFlag(1L, "Admin", null),
                new UserFlag(2L, "User", "Value2")
        );

        Assertions.assertThrows(UserServiceException.class, () -> userService.validateUserFlags(userFlags));
    }*/
}
