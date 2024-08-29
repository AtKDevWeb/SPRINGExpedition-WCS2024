package org.wcs.myBlog.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wcs.myBlog.repository.ArticleAuthorRepository;

@RestController
@RequestMapping("/articleAuthors")
public class ArticleAuthorController {

    private final ArticleAuthorRepository articleAuthorRepository;

    public ArticleAuthorController(ArticleAuthorRepository articleAuthorRepository) {
        this.articleAuthorRepository = articleAuthorRepository;
    }
}
