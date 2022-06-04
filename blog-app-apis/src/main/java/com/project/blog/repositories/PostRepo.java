package com.project.blog.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.blog.entities.Category;
import com.project.blog.entities.Post;
import com.project.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	
	List<Post> findByUser(User user);
	
	
	List<Post> findByCategory(Category category,PageRequest p);
	
	@Query("select p from Post p where lower(p.title) like concat('%', :keyword,'%')")
//	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("keyword") String keyword);
	
}
 