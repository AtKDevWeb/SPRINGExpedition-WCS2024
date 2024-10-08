package org.wcs.myBlog.mappers;

import org.springframework.stereotype.Component;
import org.wcs.myBlog.DTO.ImageDTO;
import org.wcs.myBlog.models.Article;
import org.wcs.myBlog.models.Image;

import java.util.stream.Collectors;

@Component
public class ImageMapper {

    //Mapper
    public ImageDTO convertImageToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setPath(image.getPath());
        if (image.getArticles() != null ) {
            imageDTO.setArticlesIds(image.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
        }
        return imageDTO;
    }

}
