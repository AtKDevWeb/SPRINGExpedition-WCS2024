package org.wcs.myBlog.mappers;

import org.springframework.stereotype.Component;
import org.wcs.myBlog.DTO.AuthorDTO;
import org.wcs.myBlog.models.Author;

@Component
public class AuthorMapper {
    // Mapper
    public AuthorDTO convertToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());

        return authorDTO;
    }
}
