package com.project.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.project.blog.payloads.CategoryDto;
import com.project.blog.payloads.CommentDto;
import com.project.blog.payloads.UserDto;

import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "post_title", length = 100, nullable = false)
	private String title;
	
	@Column(length = 10000)
	private String content;
	
	private String image;
	
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name = "category_Id")
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Comment> comment = new HashSet<>();

}
