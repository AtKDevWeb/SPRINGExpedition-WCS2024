package org.wcs.myBlog.DTO;

import jakarta.persistence.*;
import org.wcs.myBlog.models.Article;

import java.util.List;

public class ImageDTO {
    private long id;
    private String path;
    private List<Long> articlesIds;


    //Getter
    public long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public List<Long> getArticlesIds() {
        return articlesIds;
    }

    //Setter
    public void setId(long id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setArticlesIds(List<Long> articlesIds) {
        this.articlesIds = articlesIds;
    }
}
