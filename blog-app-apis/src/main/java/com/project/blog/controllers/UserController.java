package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.UserDto;
import com.project.blog.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	// User Created
	@RequestMapping(value = "/rest/api/createUser", method = RequestMethod.POST)
	public ResponseEntity<UserDto> createUser(@Validated @RequestBody UserDto userDto) {
		
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	// Update User
	@RequestMapping(value = "/rest/api/updateUser/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<UserDto> updateUser(@Validated @RequestBody UserDto userDto, @PathVariable("userId") Integer uId) {
		
		UserDto updatedUser = this.userService.updateUser(userDto, uId);
		return ResponseEntity.ok(updatedUser);
	}
	
	
	// Admin Only
	//Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/rest/api/deleteUser/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId) {
		
		this.userService.deleteUser(uId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
		
	}
	
	// get single user
	@RequestMapping(value = "/rest/api/getUserById/{userId}", method = RequestMethod.GET)
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer uId) {
		
		UserDto userDto = this.userService.getUserById(uId);
		return ResponseEntity.ok(userDto);
	}
	
	// get All User
	@RequestMapping(value = "/rest/api/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<List<UserDto>> getAllUsers() {
		
		List<UserDto> allUsers = this.userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
}
