package org.wcs.myBlog.DTO;

import jakarta.persistence.*;
import org.wcs.myBlog.models.Article;
import org.wcs.myBlog.models.Author;

public class ArticleAuthorDTO {

    private long id;
    private Article article;
    private Author author;
    private String contribution;

    //Getter
    public Article getArticle() {
        return article;
    }

    public Author getAuthor() {
        return author;
    }

    public String getContribution() {
        return contribution;
    }

    public long getId() {
        return id;
    }

    //Setter
    public void setArticle(Article article) {
        this.article = article;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public void setId(long id) {
        this.id = id;
    }
}
