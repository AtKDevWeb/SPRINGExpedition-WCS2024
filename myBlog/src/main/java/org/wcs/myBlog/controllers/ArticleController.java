package org.wcs.myBlog.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ArticleDTO;
import org.wcs.myBlog.mappers.ArticleMapper;
import org.wcs.myBlog.models.Article;
import org.wcs.myBlog.services.ArticleService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    //Declaration du constructeur et injection du repository
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
    }


    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
      ArticleDTO savedArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticle() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    //ReadOne
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable long id) {
        ArticleDTO article = articleService.getArticleById(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    //ReadByTiltle
    @GetMapping("/search-title")
    public ResponseEntity<List<ArticleDTO>> getArticleByTitle(@RequestParam String title) {
        List<ArticleDTO> articles = articleService.getArticleByTitle(title);
        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);

    }

    //ReadByContentContainingString
    @GetMapping("/search-term")
    public ResponseEntity<List<ArticleDTO>> getArticleByTerm(@RequestParam String term) {
        List<ArticleDTO> articles = articleService.getArticleByTerm(term);
        if (articles == null||articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    //ReadByCreatedAtAfterDate
    @GetMapping("/search-date")
    public ResponseEntity<List<ArticleDTO>> getArticleByCreatedAtAfter(@RequestParam LocalDateTime date) {
        List<ArticleDTO> articles = articleService.getArticleByCreatedAtAfter(date);
        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }
    //Read5TopArticleCreated
    @GetMapping("/top-5")
    public ResponseEntity<List<ArticleDTO>> getArticleByTop5() {
        List <ArticleDTO> articles = articleService.getArticleByTop5();
        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    //UpdateOne
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable long id, @RequestBody Article articleDetails) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDetails);
        if (updatedArticle == null ) {
            return ResponseEntity.notFound().build();
        }

            return ResponseEntity.ok(updatedArticle);
        }

        //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        if (articleService.deleteArticle(id)) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
