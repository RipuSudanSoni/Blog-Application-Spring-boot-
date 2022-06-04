package com.project.blog.controllers;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.config.AppConstant;
import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.services.FileService;
import com.project.blog.services.PostService;

import net.bytebuddy.implementation.bytecode.constant.DefaultValue;

@RestController
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	//Create
	@RequestMapping(value = "/rest/api/createPost/user/{userId}/category/{categotyId}",method = RequestMethod.POST)
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable("userId") Integer userId, @PathVariable("categotyId") Integer categoryId) {
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	//get Post By Id
	@RequestMapping(value = "/rest/api/get/{postId}/post", method = RequestMethod.GET)
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	// get All Posts
	@RequestMapping(value = "/rest/api/allPosts", method = RequestMethod.GET)
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
			) {
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	// delete Post
	@RequestMapping(value = "/rest/api/delete/{postId}/post", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is dileted successfuly", true), HttpStatus.OK);
		
	}
	
	//update post
	@RequestMapping(value = "/rest/api/{postId}/postUpdate",method = RequestMethod.PUT)
	public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Integer postId, @RequestBody PostDto postDto) {
		
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	
	// get Post by user
	@RequestMapping(value = "/rest/api/uaer/{userId}/post",method = RequestMethod.GET)
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId) {
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	//get post by category
	@RequestMapping(value = "/rest/api/category/{categoryId}/post",method = RequestMethod.GET)
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
			) {
		List<PostDto> posts = this.postService.getPostByCategory(categoryId, pageNo, pageSize);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	// search API
	@RequestMapping(value = "/rest/api/search/{keyWord}/post", method = RequestMethod.GET)
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keyWord") String keyWord) {
		List<PostDto> searchResult = this.postService.searchPosts(keyWord);
		return new ResponseEntity<List<PostDto>>(searchResult, HttpStatus.OK);
	}
	
	// Uploade Image or file
	@RequestMapping(value = "rest/api/{postId}/image/uploade", method = RequestMethod.POST)
	public ResponseEntity<PostDto> uploadeFile(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException {
		
		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = fileService.uploadeFile(path, image);
		postDto.setImage(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	// searve Tne Image
		@RequestMapping(value = "/rest/downloade/{imageName}/image", method = RequestMethod.GET)
		public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
			
				InputStream resource = this.fileService.getResource(path, imageName);
			   	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				StreamUtils.copy(resource, response.getOutputStream());
			
		}
}
