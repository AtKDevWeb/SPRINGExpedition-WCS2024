package org.wcs.myBlog.DTO;


import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTO {
    private long id;
    private String title;
    private String content;
    private LocalDateTime updateAt;
    private String categoryName;
    private List<String> imagePaths;
    private List<AuthorDTO> authors;

    //Getter
    public String getCategoryName() {
        return categoryName;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    //Setter
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
}
