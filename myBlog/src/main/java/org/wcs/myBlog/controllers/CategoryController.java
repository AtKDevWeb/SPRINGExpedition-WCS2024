package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.controllers.repository.CategoryRepository;
import org.wcs.myBlog.models.Category;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository CategoryRepository;
    //injection
    private CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository) {
        this.CategoryRepository = categoryRepository;
    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
            Category savedCategory = CategoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    //ReadAll
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = CategoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }
    //ReadById
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    //Update
    @PostMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        category.setName(categoryDetails.getName());
        Category savedCategory = CategoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }


    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }




}
