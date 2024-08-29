package org.wcs.myBlog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.models.Author;
import org.wcs.myBlog.repository.AuthorRepository;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Mapper


    // CRUD
    // Create
    @PostMapping
    public ResponseEntity<Author> addAuthor() {


    }
    // ReadAll
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(authors);

    }

    // ReadOneById
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {

    }
    // UpdateOneById
    @PostMapping("/{id}")

    // DeleteById
    @DeleteMapping("/{id}")

}
