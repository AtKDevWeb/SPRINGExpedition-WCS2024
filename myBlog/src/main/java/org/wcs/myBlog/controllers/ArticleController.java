package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.controllers.repository.ArticleRepository;
import org.wcs.myBlog.models.Article;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    //Declaration du constructeur et injection du repository
    private final ArticleRepository articleRepository;
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        //initialisation des variables de temps
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        //Sauvegarde des Data dans l'objet SavedArticle pour la création de l'enregistrement dans la BDD
        Article savedArticle = articleRepository.save(article);
        //Retour de la confirmation de la création du tuple dans la BDD
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticle() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }
    //ReadOne
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    //ReadByTiltle
    @GetMapping("/search-title")
    public ResponseEntity<List<Article>> getArticleByTitle(@RequestParam String title) {
        List<Article> articles = articleRepository.findByTitle(title);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    //ReadByContentContainingString
    @GetMapping("/search-term")
    public ResponseEntity<List<Article>> getArticleByTerm(@RequestParam String term) {
        List<Article> articles = articleRepository.findByContentContainingIgnoreCase(term);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    //ReadByCreatedAtAfterDate
    @GetMapping("/search-date")
    public ResponseEntity<List<Article>> getArticleByCreatedAtAfter(@RequestParam LocalDateTime date) {
        List<Article> articles = articleRepository.findByCreatedAtAfter(date);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }
    //Read5TopArticleCreated
    @GetMapping("/top-5")
    public ResponseEntity<List<Article>> getArticleByTop5() {
        List <Article> articles = articleRepository.findByTop5CreatedAtOrderByDesc();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    //UpdateOne
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody Article articleDetails) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        Article updatedArticle = articleRepository.save(article);
        return ResponseEntity.ok(updatedArticle);
    }
    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article ==null){
            return ResponseEntity.notFound().build();
        }
        articleRepository.delete(article);
        return ResponseEntity.noContent().build();
    }

}
