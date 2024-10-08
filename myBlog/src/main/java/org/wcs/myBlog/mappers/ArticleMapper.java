package org.wcs.myBlog.mappers;

import org.springframework.stereotype.Component;
import org.wcs.myBlog.DTO.ArticleDTO;
import org.wcs.myBlog.DTO.AuthorDTO;
import org.wcs.myBlog.models.Article;
import org.wcs.myBlog.models.Image;

import java.util.stream.Collectors;

@Component
public class ArticleMapper {
    //Mapper
    public ArticleDTO convertToDTO(Article article) {
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
}
