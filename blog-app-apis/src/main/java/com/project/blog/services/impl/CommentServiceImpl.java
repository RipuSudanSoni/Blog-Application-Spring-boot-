package com.project.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.CommentDto;
import com.project.blog.repositories.CommentRepo;
import com.project.blog.repositories.PostRepo;
import com.project.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CommentDto createComment(Integer postId, CommentDto commentDto) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		Comment createdComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(createdComment, CommentDto.class);
		
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = this.commentRepo.getById(commentId);
		this.commentRepo.delete(comment);
		
	}

}
