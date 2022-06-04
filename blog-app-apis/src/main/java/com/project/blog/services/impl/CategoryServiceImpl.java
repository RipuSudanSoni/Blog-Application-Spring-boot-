package com.project.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Category;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.CategoryDto;
import com.project.blog.repositories.CategoryRepo;
import com.project.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category categ = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory = this.categoryRepo.save(categ);
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cate = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		cate.setCategoryTitle(categoryDto.getCategoryTitle());
		cate.setCategoryDescription(categoryDto.getCategoryDescription());
		Category uddatedCate = this.categoryRepo.save(cate);
		
		return this.modelMapper.map(uddatedCate, CategoryDto.class);
	}

	// delete 
	
	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cate = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Categoury", "Category Id", categoryId));
		this.categoryRepo.delete(cate);
		
	}

	@Override
	public CategoryDto getCategoryById(Integer categouryId) {
		
		Category category = this.categoryRepo.findById(categouryId).orElseThrow(()-> new ResourceNotFoundException("category", "category Id", categouryId));
		CategoryDto cate = this.modelMapper.map(category, CategoryDto.class);
		return cate;
	}

	@Override
	public List<CategoryDto> getAllCategoury() {
		
		List<Category> allCategories = this.categoryRepo.findAll();
		List<CategoryDto> collect = allCategories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return collect;
	
	}

}
