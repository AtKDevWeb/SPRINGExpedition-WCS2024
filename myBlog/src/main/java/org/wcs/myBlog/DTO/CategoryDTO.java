package org.wcs.myBlog.DTO;

import java.util.List;

public class CategoryDTO {
    private long id;
    private String name;
    private List<String> articlesNames;

    public List<String> getArticlesNames() {
        return articlesNames;
    }

    public void setArticlesNames(List<String> articlesNames) {
        this.articlesNames = articlesNames;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
