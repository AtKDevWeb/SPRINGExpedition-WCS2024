package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.AuthorDTO;
import org.wcs.myBlog.models.Author;
import org.wcs.myBlog.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Mapper
    public AuthorDTO convertToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());

        return authorDTO;
    }

    // CRUD
    // Create
    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody Author author) {
        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToAuthorDTO(savedAuthor));
    }

    // ReadAll
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AuthorDTO> authorsDTOs = authors.stream()
                .map(this::convertToAuthorDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(authorsDTOs);

    }

    // ReadOneById
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToAuthorDTO(author));

    }
    // UpdateOneById
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        Author updatedAuthor = authorRepository.findById(id).orElse(null);
        if (updatedAuthor == null) {
            return ResponseEntity.notFound().build();
        }
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());

        authorRepository.save(updatedAuthor);
        return ResponseEntity.ok(convertToAuthorDTO(author));
    }

    // DeleteById
    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        authorRepository.delete(author);
        return ResponseEntity.noContent().build();
    }

}
