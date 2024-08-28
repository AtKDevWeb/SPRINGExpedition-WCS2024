package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ArticleDTO;
import org.wcs.myBlog.repository.ArticleRepository;
import org.wcs.myBlog.repository.CategoryRepository;
import org.wcs.myBlog.models.Article;
import org.wcs.myBlog.models.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    //Declaration du constructeur et injection du repository
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticleController(ArticleRepository articleRepository,
                             CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    //Mapper
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();

        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdateAt(article.getUpdatedAt());
        if (article.getCategory() != null) {
            articleDTO.setCategoryName((article.getCategory().getName()));
        }
        return articleDTO;
    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        //initialisation des variables de temps
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        //Add Category On Creation
        if(article.getCategory() !=null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
            if(category == null) {
                return ResponseEntity.badRequest().body(null);
            }
            article.setCategory(category);
        }

        //Sauvegarde des Data dans l'objet SavedArticle pour la création de l'enregistrement dans la BDD
        Article savedArticle = articleRepository.save(article);

        //Retour de la confirmation de la création du tuple dans la BDD
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedArticle));


    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticle() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articlesDTOs = articles.stream().map(this::convertToDTO).collect(Collectors.toList());

        return ResponseEntity.ok(articlesDTOs);
    }

    //ReadOne
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(article));
    }

    //ReadByTiltle
    @GetMapping("/search-title")
    public ResponseEntity<List<ArticleDTO>> getArticleByTitle(@RequestParam String title) {
        List<Article> articles = articleRepository.findByTitle(title);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articlesDTOs = articles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(articlesDTOs);

    }

    //ReadByContentContainingString
    @GetMapping("/search-term")
    public ResponseEntity<List<ArticleDTO>> getArticleByTerm(@RequestParam String term) {
        List<Article> articles = articleRepository.findByContentContainingIgnoreCase(term);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articlesDTOs = articles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(articlesDTOs);
    }

    //ReadByCreatedAtAfterDate
    @GetMapping("/search-date")
    public ResponseEntity<List<ArticleDTO>> getArticleByCreatedAtAfter(@RequestParam LocalDateTime date) {
        List<Article> articles = articleRepository.findByCreatedAtAfter(date);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articlesDTOs = articles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(articlesDTOs);
    }
    //Read5TopArticleCreated
    @GetMapping("/top-5")
    public ResponseEntity<List<ArticleDTO>> getArticleByTop5() {
        List <Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articlesDTOs = articles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(articlesDTOs);
    }

    //UpdateOne
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable long id, @RequestBody Article articleDetails) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        //add Category on Update
        Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElse(null);
        if (articleDetails.getCategory() == null) {
            return ResponseEntity.notFound().build();
        }
            article.setCategory(category);

            Article updatedArticle = articleRepository.save(article);
            return ResponseEntity.ok(convertToDTO(updatedArticle));
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
