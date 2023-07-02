package com.java.springboot.demo.controller;
import com.java.springboot.demo.entity.UserFlag;
import com.java.springboot.demo.exception.UserServiceException;
import com.java.springboot.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestApiControllerTest {

    @Mock
    private UserService userService;

    private RestApiController restApiController;

    @BeforeEach
    void setUp() {
       // MockitoAnnotations.openMocks(this);
      //  restApiController = new RestApiController(userService);
    }

    @Test
    void publishMessage_ValidMessage_ReturnsListOfUserFlags() {
        // Arrange
        String message = "User1, User2, User3";
        List<UserFlag> userFlags = new ArrayList<>();
        userFlags.add(new UserFlag(1L, "Type1", "Value1"));
        userFlags.add(new UserFlag(2L, "Type2", "Value2"));

        when(userService.processMessageData(message)).thenReturn(userFlags);
        when(userService.createUserFlag(userFlags)).thenReturn(userFlags);

        // Act
        ResponseEntity<?> responseEntity = restApiController.publishMessage(message);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userFlags, responseEntity.getBody());
        verify(userService, times(1)).processMessageData(message);
        verify(userService, times(1)).createUserFlag(userFlags);
    }

    @Test
    void publishMessage_InvalidMessage_ThrowsBadRequestException() {
        // Arrange
        String message = "Invalid message";

        when(userService.processMessageData(message)).thenThrow(new UserServiceException("Invalid message"));

        // Act
        ResponseEntity<?> responseEntity = restApiController.publishMessage(message);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid message", responseEntity.getBody());
        verify(userService, times(1)).processMessageData(message);
        verify(userService, never()).createUserFlag(anyList());
    }

    @Test
    void fetchById_ValidId_ReturnsUserFlag() {
        // Arrange
        Long id = 1L;
        UserFlag userFlag = new UserFlag(1L, "Type1", "Value1");

        when(userService.fetchUserById(id)).thenReturn(userFlag);

        // Act
        ResponseEntity<UserFlag> responseEntity = restApiController.fetchUserById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userFlag, responseEntity.getBody());
        verify(userService, times(1)).fetchUserById(id);
    }

    @Test
    void fetchById_InvalidId_ReturnsNotFoundStatus() {
        // Arrange
        Long id = 1L;

        when(userService.fetchUserById(id)).thenReturn(null);

        // Act
        ResponseEntity<UserFlag> responseEntity = restApiController.fetchUserById(id);

        // Assert
      //  assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(userService, times(1)).fetchUserById(id);
    }

    // Add more test cases for other methods in RestApiController

}
