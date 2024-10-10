package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ArticleAuthorDTO;
import org.wcs.myBlog.models.ArticleAuthor;

import org.wcs.myBlog.services.ArticleAuthorService;
import org.wcs.myBlog.services.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/articleAuthors")
public class ArticleAuthorController {

    private final ArticleAuthorService articleAuthorService;


    public ArticleAuthorController (ArticleAuthorService articleAuthorService, ArticleService articleService) {
        this.articleAuthorService = articleAuthorService;
    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<ArticleAuthorDTO> addArticleAuthor(@RequestBody ArticleAuthor articleAuthor) {
        ArticleAuthorDTO savedArticleAuthor = articleAuthorService.addArticleAuthor(articleAuthor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticleAuthor);
    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<ArticleAuthorDTO>> getAllArticleAuthors() {
        List<ArticleAuthorDTO> articlesAuthors = articleAuthorService.getAllArticleAuthors();
        if (articlesAuthors == null ||articlesAuthors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.ok(articlesAuthors);
    }
    //ReadOneById
    @GetMapping("/{id}")
    public ResponseEntity<ArticleAuthorDTO> getByArticleAuthor(@PathVariable long id){
        ArticleAuthorDTO articleAuthor = articleAuthorService.getByArticleAuthor(id);
        if (articleAuthor == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articleAuthor);
    }
    //UpdateById
    @PutMapping("/{id}")
    public ResponseEntity<ArticleAuthorDTO> updateArticleAuthor(@PathVariable long id,
                                                                @RequestBody ArticleAuthor articleAuthor) {
       ArticleAuthorDTO updatedArticleAuthor = articleAuthorService.getByArticleAuthor(id);
        if (updatedArticleAuthor == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(updatedArticleAuthor);
    }
    //DeleteById
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleAuthor(@PathVariable long id) {
        if (articleAuthorService.deleteArticleAuthor(id)) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
