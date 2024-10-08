package org.wcs.myBlog.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.CategoryDTO;
import org.wcs.myBlog.mappers.CategoryMapper;
import org.wcs.myBlog.models.Category;
import org.wcs.myBlog.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    //CRUD
    //Create
    public CategoryDTO addCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.convertCategoryToDTO(savedCategory);
    }
    //ReadAll
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::convertCategoryToDTO)
                .collect(Collectors.toList());
    }
    //ReadById
    public CategoryDTO getCategoryById( long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        return categoryMapper.convertCategoryToDTO(category);
    }

    //Update
    public CategoryDTO updateCategory( long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        category.setName(categoryDetails.getName());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.convertCategoryToDTO(savedCategory);
    }


    //Delete
    public boolean deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return false;
        }
        categoryRepository.delete(category);
        return false;
    }
}
