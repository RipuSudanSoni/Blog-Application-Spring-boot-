package com.project.blog.services;

import java.util.List;

import com.project.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer UserId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer UserId);

}
