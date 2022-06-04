package com.project.blog.services;

import java.util.List;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;

public interface PostService {
	
	// create
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	// update
	public PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	public void deletePost(Integer postId);
	
	//getById
	public PostDto getPostById(Integer postId);
	
	// getAll
	PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

	// get All Post By Category
	List<PostDto> getPostByCategory(Integer categoryId, int pageNo, int pageSize);
	
	// get All Post By User
	List<PostDto> getPostByUser(Integer userId);
	
	// search Posts
	List<PostDto> searchPosts(String keyword);
}
