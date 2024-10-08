package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ArticleDTO;
import org.wcs.myBlog.DTO.CategoryDTO;
import org.wcs.myBlog.repositories.CategoryRepository;
import org.wcs.myBlog.models.Category;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    //injection
    private final CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //Mapper
    private CategoryDTO convertCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());

        if(category.getArticles() != null){
            categoryDTO.setArticles(category.getArticles().stream().map(
                    //formatage de la donnée selon le model de donnée de l'objet articleDTO
                    article-> {
                        ArticleDTO articleDTO = new ArticleDTO();
                        articleDTO.setId(article.getId());
                        articleDTO.setTitle(article.getTitle());
                        articleDTO.setContent(article.getContent());
                        articleDTO.setUpdateAt(article.getUpdatedAt());
                        articleDTO.setCategoryName(article.getCategory().getName());
                        return articleDTO;
                    }).collect(Collectors.toList()));
        }
        return categoryDTO;
    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody Category category) {
            Category savedCategory = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertCategoryToDTO(savedCategory));
    }
    //ReadAll
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CategoryDTO> categoriesDTOs = categories.stream()
                .map(this::convertCategoryToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoriesDTOs);
    }
    //ReadById
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertCategoryToDTO(category));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable long id, @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        category.setName(categoryDetails.getName());
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(convertCategoryToDTO(savedCategory));
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
