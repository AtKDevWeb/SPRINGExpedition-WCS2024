package org.wcs.myBlog.mappers;

import org.springframework.stereotype.Component;
import org.wcs.myBlog.DTO.ArticleAuthorDTO;
import org.wcs.myBlog.models.ArticleAuthor;

@Component
public class ArticleAuthorMapper {

    //Mapper
    public ArticleAuthorDTO convertToArticleAuthorDTO (ArticleAuthor articleAuthor) {
        ArticleAuthorDTO articleAuthorDTO = new ArticleAuthorDTO();
        articleAuthorDTO.setId(articleAuthor.getId());

        articleAuthorDTO.setArticle(articleAuthor.getArticle());
        articleAuthorDTO.setAuthor(articleAuthor.getAuthor());

        articleAuthorDTO.setContribution(articleAuthor.getContribution());

        return articleAuthorDTO;

    }


}
