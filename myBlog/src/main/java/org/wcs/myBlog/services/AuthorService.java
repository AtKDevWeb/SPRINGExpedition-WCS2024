package org.wcs.myBlog.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.AuthorDTO;
import org.wcs.myBlog.mappers.AuthorMapper;
import org.wcs.myBlog.models.Author;
import org.wcs.myBlog.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
    }

    // CRUD
    // Create
    public AuthorDTO addAuthor(@RequestBody Author author) {
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.convertToAuthorDTO(savedAuthor);
    }

    // ReadAll
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            return null;

        }
        return authors.stream().map(authorMapper::convertToAuthorDTO).collect(Collectors.toList());
    }

    // ReadOneById
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return null;

        }

        return authorMapper.convertToAuthorDTO(author);

    }
    // UpdateOneById
    public AuthorDTO updateAuthor( Long id, Author author) {
        Author updatedAuthor = authorRepository.findById(id).orElse(null);
        if (updatedAuthor == null) {
            return null;
        }
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());

        authorRepository.save(updatedAuthor);
        return authorMapper.convertToAuthorDTO(author);
    }

    // DeleteById
    public boolean deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return false;
        }
        authorRepository.delete(author);
        return false;

    }
}
