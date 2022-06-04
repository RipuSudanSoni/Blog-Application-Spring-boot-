package com.project.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.project.blog.entities.Role;

//import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "UserName must be min of 4 characters !!")
	private String name;
	
	@Email(message = "Email address is not valid !!")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be between of 3 to 10 Chatracters !!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

}
