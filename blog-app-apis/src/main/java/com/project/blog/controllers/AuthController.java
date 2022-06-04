package com.project.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.exceptions.ApiException;
import com.project.blog.payloads.JwtAuthRequest;
import com.project.blog.payloads.JwtAuthResponse;
import com.project.blog.payloads.UserDto;
import com.project.blog.security.JwtTokenHelper;
import com.project.blog.services.UserService;

@RestController
public class AuthController {
	
	Logger logger =  LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
	public ResponseEntity<JwtAuthResponse> createtoken(@RequestBody JwtAuthRequest request) throws Exception {
		
		logger.info("request :- {}",request);
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		 
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
	    return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK); 
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException e) {
			System.out.println("Invalid Details !! "+e);
			throw new ApiException("Invalid UserName or Password !!");
		}
		
		
	}
	
	//register new User Api
	@RequestMapping(value = "/api/auth/register", method = RequestMethod.POST)
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
		
	}
}
