package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ArticleDTO;
import org.wcs.myBlog.DTO.AuthorDTO;
import org.wcs.myBlog.models.*;
import org.wcs.myBlog.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    //Declaration du constructeur et injection du repository
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final AuthorRepository authorRepository;
    private final ArticleAuthorRepository articleAuthorRepository;

    public ArticleController(ArticleRepository articleRepository,
                             CategoryRepository categoryRepository,
                             ImageRepository imageRepository,
                             AuthorRepository authorRepository,
                             ArticleAuthorRepository articleAuthorRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.authorRepository = authorRepository;
        this.articleAuthorRepository = articleAuthorRepository;
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
        if (article.getImages() != null) {
            articleDTO.setImagePaths((article.getImages().stream().map(Image::getPath).collect(Collectors.toList())));
        }
        if (article.getArticleAuthors()!=null) {
            articleDTO.setAuthors( article.getArticleAuthors().stream()
                    .filter(articleAuthor -> articleAuthor.getAuthor() != null)
                    .map(articleAuthor -> {
                        AuthorDTO authorDTO = new AuthorDTO();
                        authorDTO.setId(articleAuthor.getAuthor().getId());
                        authorDTO.setFirstName(articleAuthor.getAuthor().getFirstName());
                        authorDTO.setLastName(articleAuthor.getAuthor().getLastName());

                        return authorDTO;
                    })
                    .collect(Collectors.toList()));
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

        //Add CategoryMapper On Creation
        if(article.getCategory() !=null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
            if(category == null) {
                return ResponseEntity.badRequest().body(null);
            }
            article.setCategory(category);
        }
    //Verification de l'existence d'une liste d'image et de la completion de celle-ci
        if (article.getImages() != null ){
            List<Image> validImages = new ArrayList<>();
            for (Image image : article.getImages()) {
                //Verification des images existantes
                if (image.getId() > 0){
                    Image existingImage = imageRepository.findById((image.getId()).orElse(null));
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    }else{
                        return ResponseEntity.badRequest().body(null); // return error if image not found
                    }
                }else {// created new image
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages);
        }

        //Sauvegarde des Data dans l'objet SavedArticle pour la création de l'enregistrement dans la BDD
        Article savedArticle = articleRepository.save(article);

        //On sauvegarde d'abord l'objet avant de creer la relation definitive sinon on ne peut pas affecter l'article
        // à sa relation car il n'existe pas
        if(article.getArticleAuthors() !=null){
            for (ArticleAuthor  articleAuthor : article.getArticleAuthors()) {
                Author author = articleAuthor.getAuthor();
                author = authorRepository.findById(author.getId()).orElse(null);
                if(author == null) {
                    return ResponseEntity.badRequest().body(null);
                }

                articleAuthor.setAuthor(author);
                articleAuthor.setArticle(savedArticle);
                articleAuthor.setContribution(articleAuthor.getContribution());

                articleAuthorRepository.save(articleAuthor);
            }
        }
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

        //add CategoryMapper on Update
        Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElse(null);
        if (articleDetails.getCategory() == null) {
            return ResponseEntity.notFound().build();
        }
        article.setCategory(category);

        //Verification de l'existence d'une liste d'image et de la completion de celle-ci
        if (article.getImages() != null) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : articleDetails.getImages()) {
                //Verification des images existantes
                if (image.getId() > 0){
                    Image existingImage = imageRepository.findById((image.getId()).orElse(null));
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    }else{
                        return ResponseEntity.badRequest().body(null);
                    }
                }else {
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            //Metre à jours la liste d'image
            article.setImages(validImages);
        }else {
            //Si aucunes image est fournie, on nettoie la liste des images associés.
            article.getImages().clear();
        }
        if(articleDetails.getArticleAuthors() != null) {
            //Manual Suppress oldAuthors)
            for (ArticleAuthor oldArticleAuthor : articleDetails.getArticleAuthors()) {
                articleAuthorRepository.delete(oldArticleAuthor);
            }
        }

            List<ArticleAuthor> updateArticleAuthors = new ArrayList<>();

            for (ArticleAuthor  articleAuthorDetails : articleDetails.getArticleAuthors()) {
                Author author = articleAuthorDetails.getAuthor();
                author = authorRepository.findById(author.getId()).orElse(null);
                if(author == null) {
                    return ResponseEntity.badRequest().body(null);
                }
                //Creer et associer la nouvelle relation
                ArticleAuthor newArticleAuthor = new ArticleAuthor();
                newArticleAuthor.setAuthor(author);
                newArticleAuthor.setArticle(article);
                newArticleAuthor.setContribution(articleAuthorDetails.getContribution());

                updateArticleAuthors.add(newArticleAuthor);
            }
        for (ArticleAuthor articleAuthor : updateArticleAuthors) {
            articleAuthorRepository.save(articleAuthor);
        }

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
        //Suppresion de toutes les relation lié à l'article
        if (article.getArticleAuthors() != null){
            for (ArticleAuthor  articleAuthor : article.getArticleAuthors()) {
                articleAuthorRepository.delete(articleAuthor);
            }
        }
        articleRepository.delete(article);
        return ResponseEntity.noContent().build();
    }

}
