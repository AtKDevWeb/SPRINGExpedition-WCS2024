package org.wcs.myBlog.models;

import jakarta.persistence.*;

@Entity
public class ArticleAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @Column(nullable = false, length = 50)
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
