package com.project.blog.payloads;

import java.util.List;

import com.project.blog.entities.Post;

import lombok.Data;

@Data
public class PostResponse {
	
	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPage;
    private boolean lastPage;
	

}
