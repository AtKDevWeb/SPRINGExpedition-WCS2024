package org.wcs.myBlog.DTO;

import java.util.List;

public class CategoryDTO {
    private long id;
    private String name;
    private List<ArticleDTO> articles;

    //getter

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //Setter
    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
