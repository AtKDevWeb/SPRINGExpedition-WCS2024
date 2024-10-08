package org.wcs.myBlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wcs.myBlog.models.ArticleAuthor;

public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, Long> {

}
