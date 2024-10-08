package org.wcs.myBlog.repositories;

import org.wcs.myBlog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
}
