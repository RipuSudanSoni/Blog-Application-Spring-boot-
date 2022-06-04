package com.project.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.blog.controllers.AuthController;
import com.project.blog.entities.Category;
import com.project.blog.entities.Post;
import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.repositories.CategoryRepo;
import com.project.blog.repositories.PostRepo;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	Logger logger =  LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
	
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImage("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = this.postRepo.save(post);
		
		return this.modelMapper.map(createdPost, PostDto.class);
		
		
	}

	// update Post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, postDto.getClass());
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	// get All Posts
	@Override
	public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		logger.info("All Posts are :- {}",allPosts);
		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
 		
		postResponse.setContent(postDtos);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		logger.info("PostResponse are :- {}",postResponse);
		return postResponse;
	}

	// find Post by Category
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId, int pageNo, int pageSize) {
		
		PageRequest p = PageRequest.of(pageNo, pageSize);
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		Page<Post> pagePost = (Page<Post>) this.postRepo.findByCategory(cat, p);
		List<Post> allPost = pagePost.getContent();
        List<PostDto> postDtos = allPost.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	
//	// find Post by Category
//		@Override
//		public List<PostDto> getPostByCategory(Integer categoryId) {
//			Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
//			List<Post> posts = this.postRepo.findByCategory(cat);
//	        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//			return postDtos;
//		}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle(keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
