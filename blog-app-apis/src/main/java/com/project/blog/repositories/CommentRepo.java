package com.project.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
