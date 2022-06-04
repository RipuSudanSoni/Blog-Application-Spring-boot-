package com.project.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.CategoryDto;
import com.project.blog.services.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	// create
	@RequestMapping(value = "/rest/api/createCategory", method = RequestMethod.POST)
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto category = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
		
	}
	
	// update
	@RequestMapping(value = "/rest/api/updateCategory/{categoryId}", method = RequestMethod.PUT)
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer cateId) {
		
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, cateId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	//delete
	@RequestMapping(value = "/rest/api/deleteCategory/{categoryId}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is dileted successfuly", true), HttpStatus.OK);
	}
	
	//getById
	@RequestMapping(value = "/rest/api/getById/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId) {
		
		CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}
	
	//getAll
	@RequestMapping(value = "/rest/api/getAllCategory", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		
		List<CategoryDto> allCategoury = this.categoryService.getAllCategoury();
		return ResponseEntity.ok(allCategoury);
	}

}
