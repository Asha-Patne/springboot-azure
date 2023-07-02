package com.java.springboot.demo.controller;

import com.java.springboot.demo.entity.UserFlag;
import com.java.springboot.demo.exception.UserServiceException;
import com.java.springboot.demo.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "basicauth")
@OpenAPIDefinition(info = @Info(title = "User API", version = "v1"))
@Log4j2
public class RestApiController {

	@Autowired
	private UserService userService;

	@PostMapping("/publishMessage")
	public ResponseEntity<?> publishMessage(@RequestBody String message) {
		log.info("Inside Controller");
		try {
			List<UserFlag> userFlagList = userService.processMessageData(message);
			List<UserFlag> saveUserFlag = userService.createUserFlag(userFlagList);
			return ResponseEntity.ok(saveUserFlag);
		} catch (UserServiceException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}


	@GetMapping("/fetchById")
	public ResponseEntity<UserFlag> fetchUserById(@RequestParam Long id){
		UserFlag userFlag = userService.fetchUserById(id);
		return new ResponseEntity<>(userFlag, HttpStatus.OK);
	}

	@GetMapping("/fetchUserByIdAndType")
	public ResponseEntity<UserFlag> fetchByIdAndType(@RequestParam Long id, @RequestParam String type) {
		UserFlag userFlag = userService.fetchByUserIdAndType(id, type);
			if (userFlag != null) {
				return new ResponseEntity<>(userFlag, HttpStatus.OK);
			}
			else { return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}

	@PutMapping("/updateUser")
	public ResponseEntity<UserFlag> updateUser(@RequestBody UserFlag user){

		UserFlag updatedUser = userService.updateUserFlag(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam Long userFlagId){
		userService.deleteUser(userFlagId);
		return new ResponseEntity<>("User successfully deleted!"+userFlagId, HttpStatus.OK);
	}

	@DeleteMapping("/deleteAllUser")
	public ResponseEntity<String> deleteAllUser(){
		userService.deleteAllUser();
		return new ResponseEntity<>("All User successfully deleted!", HttpStatus.OK);
	}
}
