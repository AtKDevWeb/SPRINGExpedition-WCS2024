package org.wcs.myBlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wcs.myBlog.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
