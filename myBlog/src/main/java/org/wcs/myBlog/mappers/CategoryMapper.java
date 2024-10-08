package org.wcs.myBlog.mappers;

import org.springframework.stereotype.Component;
import org.wcs.myBlog.DTO.ArticleDTO;
import org.wcs.myBlog.DTO.CategoryDTO;
import org.wcs.myBlog.models.Category;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    //Mapper
    public CategoryDTO convertCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());

        if(category.getArticles() != null){
            categoryDTO.setArticles(category.getArticles().stream().map(
                    //formatage de la donnée selon le model de donnée de l'objet articleDTO
                    article-> {
                        ArticleDTO articleDTO = new ArticleDTO();
                        articleDTO.setId(article.getId());
                        articleDTO.setTitle(article.getTitle());
                        articleDTO.setContent(article.getContent());
                        articleDTO.setUpdateAt(article.getUpdatedAt());
                        articleDTO.setCategoryName(article.getCategory().getName());
                        return articleDTO;
                    }).collect(Collectors.toList()));
        }
        return categoryDTO;
    }

}
