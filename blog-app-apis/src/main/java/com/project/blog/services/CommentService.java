package com.project.blog.services;


import com.project.blog.payloads.CommentDto;

public interface CommentService {
	
	//create
	public CommentDto createComment(Integer postId, CommentDto commentDto);
	
	// delete
	public void deleteComment(Integer commentId);

}
