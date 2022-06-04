package com.project.blog.services;

import java.util.List;

import com.project.blog.payloads.CategoryDto;


public interface CategoryService {
	
	//create
	public CategoryDto createCategory( CategoryDto categoryDto);
	
	// update
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	public void deleteCategory(Integer categoryId);
	
	//getById
	public CategoryDto getCategoryById(Integer categouryId);
	
	//getAll
	public List<CategoryDto> getAllCategoury();

}
