package org.wcs.myBlog.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ArticleDTO;
import org.wcs.myBlog.exceptions.RessourceNotFoundException;
import org.wcs.myBlog.mappers.ArticleMapper;
import org.wcs.myBlog.models.*;
import org.wcs.myBlog.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final AuthorRepository authorRepository;
    private final ArticleAuthorRepository articleAuthorRepository;

    //Constructor


    public ArticleService(ArticleAuthorRepository articleAuthorRepository,
                          ArticleRepository articleRepository,
                          ArticleMapper articleMapper,
                          CategoryRepository categoryRepository,
                          ImageRepository imageRepository,
                          AuthorRepository authorRepository) {
        this.articleAuthorRepository = articleAuthorRepository;
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.authorRepository = authorRepository;
    }

    //CRUD
    //Create
    public ArticleDTO createArticle(Article article) {
        //initialisation des variables de temps
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        //Add CategoryMapper On Creation
        if(article.getCategory() !=null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElseThrow(()->
                    new RessourceNotFoundException("La catégorie avec l'ID"+ article.getCategory().getId() +"liée à l'article n'a pas été trouvé"));
            article.setCategory(category);
        }
        //Verification de l'existence d'une liste d'image et de la completion de celle-ci
        if (article.getImages() != null && !article.getImages().isEmpty()){
            List<Image> validImages = new ArrayList<>();

            for (Image image : article.getImages()) {
               if (image.getId()>0){
                   //Verification des images existantes
               Image existingImage = imageRepository.findById(image.getId())
                                                    .orElseThrow(()-> new RessourceNotFoundException("L'image avec l'ID"+ image.getId() + "n'a pas été trouvé"));
                        // return error if image not found
                    validImages.add(existingImage);// Ajout de l'image existante
                    }
                else {// created new image
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
        if(article.getArticleAuthors() !=null && !article.getArticleAuthors().isEmpty()){
//            for (ArticleAuthor  articleAuthor : article.getArticleAuthors()) {
//                Author author = articleAuthor.getAuthor();
//                author = authorRepository.findById(author.getId())
//                        .orElseThrow(()-> new RessourceNotFoundException(
//                                "L'auteur avec l'ID"+ author.getId() + "n'a pas été trouvé"));
//                articleAuthor.setAuthor(author);
//                articleAuthor.setArticle(savedArticle);
//                articleAuthor.setContribution(articleAuthor.getContribution());

                //nouvelle structure de code pour gêrer la spécificité de la fonction lambda : celle-ci ne peut
                // pas prendre de paramtère pouvant changer de valeur.
            for (ArticleAuthor  articleAuthor : article.getArticleAuthors()) {
                Author originalAuthor = articleAuthor.getAuthor();

                Author foundAuthor = authorRepository.findById(originalAuthor.getId())
                        .orElseThrow(()-> new RessourceNotFoundException(
                                "L'auteur avec l'ID"+ originalAuthor.getId() + "n'a pas été trouvé"));
                articleAuthor.setAuthor(foundAuthor);
                articleAuthor.setArticle(savedArticle);
                articleAuthor.setContribution(articleAuthor.getContribution());

                articleAuthorRepository.save(articleAuthor);
            }
        }
        //Retour de la création du tuple dans la BDD
        return articleMapper.convertToDTO(savedArticle);
    }
    
    //Read All
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(articleMapper::convertToDTO).collect(Collectors.toList());
    }

    //ReadOne
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(()->
                new RessourceNotFoundException("L'article avec l'ID"+ id + "n'a pas été trouvé"));
        return articleMapper.convertToDTO(article);
    }

    //ReadByTiltle
    public List<ArticleDTO> getArticleByTitle(String title) {
        List<Article> articles = articleRepository.findByTitle(title);
        if (articles.isEmpty()) {
            return null;
        }
        return articles.stream()
                .map(articleMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    //ReadByContentContainingString
    public List<ArticleDTO> getArticleByTerm(String term) {
        List<Article> articles = articleRepository.findByContentContainingIgnoreCase(term);
        if (articles.isEmpty()) {
            return null;
        }
        return articles.stream()
                .map(articleMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    //ReadByCreatedAtAfterDate
    public List<ArticleDTO> getArticleByCreatedAtAfter(@RequestParam LocalDateTime date) {
        List<Article> articles = articleRepository.findByCreatedAtAfter(date);
        if (articles.isEmpty()) {
            return null;
        }
         return articles.stream()
                .map(articleMapper::convertToDTO)
                .collect(Collectors.toList());       
    }
    //Read5TopArticleCreated
    public List<ArticleDTO> getArticleByTop5() {
        List <Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
        if (articles.isEmpty()) {
            return null;
        }
         return articles.stream()
                .map(articleMapper::convertToDTO)
                .collect(Collectors.toList());       
    }

    //UpdateOne
    public ArticleDTO updateArticle( long id, Article articleDetails) {
        Article article = articleRepository.findById(id).orElseThrow(()->
                new RessourceNotFoundException("L'article avec l'ID"+ id + "n'a pas été trouvé"));
        if (article == null) {
            return null;
        }
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        //add CategoryMapper on Update
        Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElseThrow(()->
                new RessourceNotFoundException("La catégorie avec l'ID"+ id + "n'a pas été trouvée"));
        if (articleDetails.getCategory() == null) {
            return null;
        }
        article.setCategory(category);

        //Verification de l'existence d'une liste d'image et de la completion de celle-ci
        if (article.getImages() != null) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : articleDetails.getImages()) {
                //Verification des images existantes
                if (image.getId() > 0){
                    Image existingImage = imageRepository.findById(image.getId()).orElseThrow(()->
                            new RessourceNotFoundException("L'image avec l'ID"+ image.getId() + "lié à l'article id"+ id +"n'a pas été trouvée"));
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    }else{
                        return null;
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
            Author originaleAuthor = articleAuthorDetails.getAuthor();

            Author foundAuthor = authorRepository.findById(originaleAuthor.getId()).orElseThrow(()->
                    new RessourceNotFoundException("L'auter avec l'ID"+ originaleAuthor.getId() + "lié à l'article id"+ id +"n'a pas été trouvée"));;
            if( foundAuthor == null) {
                return null;
            }
            //Creer et associer la nouvelle relation
            ArticleAuthor newArticleAuthor = new ArticleAuthor();
            newArticleAuthor.setAuthor(foundAuthor);
            newArticleAuthor.setArticle(article);
            newArticleAuthor.setContribution(articleAuthorDetails.getContribution());

            updateArticleAuthors.add(newArticleAuthor);
        }
        for (ArticleAuthor articleAuthor : updateArticleAuthors) {
            articleAuthorRepository.save(articleAuthor);
        }

        Article updatedArticle = articleRepository.save(article);
        return articleMapper.convertToDTO(updatedArticle);
    }

    //Delete
    public boolean deleteArticle(long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null){
            return false;

        }
        //Suppresion de toutes les relation liées à l'article
        if (article.getArticleAuthors() != null){
            for (ArticleAuthor  articleAuthor : article.getArticleAuthors()) {
                articleAuthorRepository.delete(articleAuthor);
            }
        }
        articleAuthorRepository.deleteAll(article.getArticleAuthors());
        articleRepository.delete(article);
        return false;
    }


}
