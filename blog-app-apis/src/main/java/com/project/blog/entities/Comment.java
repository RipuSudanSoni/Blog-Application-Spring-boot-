package com.project.blog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer comment_Id;
	
	@Column(name = "content")
	private String content;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id")				// I have to create
//	private User user;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

}
