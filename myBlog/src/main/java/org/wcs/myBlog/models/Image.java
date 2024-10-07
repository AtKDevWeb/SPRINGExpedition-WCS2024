package org.wcs.myBlog.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255 )
    private String path;

    @ManyToMany(mappedBy = "image")
    private List<Article> articles;

    //getter
    public long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public List<Article> getArticles() {
        return articles;
    }

    //setter
    public void setId(long id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
