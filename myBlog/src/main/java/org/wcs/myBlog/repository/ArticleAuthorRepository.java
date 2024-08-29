package org.wcs.myBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wcs.myBlog.models.ArticleAuthor;

public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, Long> {

}
