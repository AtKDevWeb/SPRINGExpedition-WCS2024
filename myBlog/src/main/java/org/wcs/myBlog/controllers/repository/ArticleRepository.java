package org.wcs.myBlog.controllers.repository;

import org.wcs.myBlog.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
