package org.wcs.myBlog.services;


import org.springframework.stereotype.Service;
import org.wcs.myBlog.DTO.ArticleAuthorDTO;
import org.wcs.myBlog.mappers.ArticleAuthorMapper;
import org.wcs.myBlog.mappers.ArticleMapper;
import org.wcs.myBlog.models.ArticleAuthor;
import org.wcs.myBlog.repositories.ArticleAuthorRepository;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class ArticleAuthorService {

    private final ArticleAuthorRepository articleAuthorRepository;
    private final ArticleAuthorMapper articleAuthorMapper;

    public ArticleAuthorService(ArticleMapper articleMapper, ArticleAuthorRepository articleAuthorRepository, ArticleAuthorMapper articleAuthorMapper) {
        this.articleAuthorRepository = articleAuthorRepository;
        this.articleAuthorMapper = articleAuthorMapper;
    }

    //CRUD
    //Create
    public ArticleAuthorDTO addArticleAuthor(ArticleAuthor articleAuthor) {
        ArticleAuthor savedArticleAuthor = articleAuthorRepository.save(articleAuthor);
        return articleAuthorMapper.convertToDTO(savedArticleAuthor);
    }

    //ReadAll
    public List<ArticleAuthorDTO> getAllArticleAuthors() {
        List<ArticleAuthor> articlesAuthors = articleAuthorRepository.findAll();
        if (articlesAuthors.isEmpty()) {
            return null;
        }
        return articlesAuthors.stream()
                .map(articleAuthorMapper::convertToArticleAuthorDTO)
                .collect(Collectors.toList());
    }

    //ReadOneById
    public ArticleAuthorDTO getByArticleAuthor(long id){
        ArticleAuthor articleAuthor = articleAuthorRepository.findById(id).orElse(null);
        if (author == null) {
            return null;

        }
        assert articleAuthor != null : "ArticleAuthor object is null";
        return articleAuthorMapper.convertToArticleAuthorDTO(articleAuthor);
    }

    //UpdateByID
    public ArticleAuthorDTO updateArticleAuthor(long id, ArticleAuthor articleAuthor) {
        ArticleAuthor updatedArticleAuthor = articleAuthorRepository.findById(id).orElse(null);
        if (updatedArticleAuthor == null) {
            return null;

        }
        updatedArticleAuthor.setContribution(articleAuthor.getContribution());

        articleAuthorRepository.save(updatedArticleAuthor);

        return articleAuthorMapper.convertToArticleAuthorDTO(updatedArticleAuthor);
    }

    //DeleteByID
    public boolean deleteArticleAuthor( long id) {
        ArticleAuthor articleAuthor = articleAuthorRepository.findById(id).orElse(null);
        if (articleAuthor == null) {
            return false;
        }

        articleAuthorRepository.delete(articleAuthor);
        return false;

    }

}


}
