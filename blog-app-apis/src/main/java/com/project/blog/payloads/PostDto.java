package com.project.blog.payloads;

import java.util.Date;
import java.util.Set;

import com.project.blog.entities.Comment;

import lombok.Data;

@Data
public class PostDto {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String image;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comment;
	
	
	

}
