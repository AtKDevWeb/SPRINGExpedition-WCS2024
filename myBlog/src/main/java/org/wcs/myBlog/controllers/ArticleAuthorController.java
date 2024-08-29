package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ArticleAuthorDTO;
import org.wcs.myBlog.models.ArticleAuthor;
import org.wcs.myBlog.models.Author;
import org.wcs.myBlog.repository.ArticleAuthorRepository;
import org.wcs.myBlog.repository.ArticleRepository;
import org.wcs.myBlog.repository.AuthorRepository;

import java.util.List;

@RestController
@RequestMapping("/articleAuthors")
public class ArticleAuthorController {

    private final ArticleAuthorRepository articleAuthorRepository;
    private final AuthorRepository authorRepository;
    private final ArticleRepository articleRepository;

    public ArticleAuthorController(ArticleAuthorRepository articleAuthorRepository, AuthorRepository authorRepository, ArticleRepository articleRepository) {
        this.articleAuthorRepository = articleAuthorRepository;
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
    }

    //Mapper
    private ArticleAuthorDTO convertToArticleAuthorDTO (ArticleAuthor articleAuthor) {
        ArticleAuthorDTO articleAuthorDTO = new ArticleAuthorDTO();
        articleAuthorDTO.setId(articleAuthor.getId());

        articleAuthorDTO.setArticle(articleAuthor.getArticle());
        articleAuthorDTO.setAuthor(articleAuthor.getAuthor());

        articleAuthorDTO.setContribution(articleAuthor.getContribution());

        return articleAuthorDTO;

    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<ArticleAuthorDTO> addArticleAuthor(@RequestBody ArticleAuthor articleAuthor) {
        ArticleAuthor savedArticleAuthor = articleAuthorRepository.save(articleAuthor);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToArticleAuthorDTO(savedArticleAuthor));
    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<ArticleAuthorDTO>> getAllArticleAuthors() {
        List<ArticleAuthor> articlesAuthors = articleAuthorRepository.findAll();
        if (articlesAuthors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleAuthorDTO> articleAuthorsDTOs = articlesAuthors.stream()
                .map(this::convertToArticleAuthorDTO)
                .collect(Collectors.toLIst());

        return ResponseEntity.ok(articleAuthorsDTOs);
    }
    //ReadOneById
    @GetMapping("/{id}")
    public ResponseEntity<ArticleAuthorDTO> getByArticleAuthor(@PathVariable long id){
        ArticleAuthor articleAuthor = articleAuthorRepository.findById(id).orElse(null);
        if (author == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(convertToArticleAuthorDTO(articleAuthor));
    }
    //UnpdateById
    @PutMapping("/{id}")
    public ResponseEntity<ArticleAuthorDTO> updateArticleAuthor(@PathVariable long id, @RequestBody ArticleAuthor articleAuthor) {
       ArticleAuthor updatedArticleAuthor = articleAuthorRepository.findById(id).orElse(null);
        if (updatedArticleAuthor == null) {
            return ResponseEntity.noContent().build();
        }
        updatedArticleAuthor.setContribution(articleAuthor.getContribution());

        articleAuthorRepository.save(updatedArticleAuthor);

        return ResponseEntity.ok(convertToArticleAuthorDTO(updatedArticleAuthor));
    }
    //DeleteById
    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleAuthor> deleteArticleAuthor(@PathVariable long id) {*
    ArticleAuthor articleAuthor = articleAuthorRepository.findById(id).orElse(null);
    if (articleAuthor == null) {
        return ResponseEntity.noContent().build();
    }
    articleAuthorRepository.delete(articleAuthor);
    return ResponseEntity.noContent().build();
    }

}
