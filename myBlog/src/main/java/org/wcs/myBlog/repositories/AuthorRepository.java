package org.wcs.myBlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wcs.myBlog.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}