package com.project.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.CommentDto;
import com.project.blog.services.CommentService;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	
	// create Comment
	@RequestMapping(value = "rest/api/comment/{postId}/create", method = RequestMethod.POST)
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId) {
		
		CommentDto createdComment = this.commentService.createComment(postId, commentDto);
		
		return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
	}
	
	// delete comment
	@RequestMapping(value = "rest/api/delete/{commentId}/comment", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {
		
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment delete Successfully !!", true), HttpStatus.OK);
		
	}
	
	
}
