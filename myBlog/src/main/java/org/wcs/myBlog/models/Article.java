package org.wcs.myBlog.models;

import jakarta.persistence.*;
import org.springframework.web.servlet.tags.form.TextareaTag;

import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column (nullable = false, length = 50)
    private String title;

    @Column (columnDefinition = "TEXT")
    private String content;

    @Column (nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column (nullable = false)
    private LocalDateTime updatedAt;

    //Contructor : optionnal while , the reflexion create this

    //Getters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
