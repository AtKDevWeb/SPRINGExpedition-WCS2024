package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ImageDTO;
import org.wcs.myBlog.models.Image;
import org.wcs.myBlog.models.Article;
import org.wcs.myBlog.repository.ArticleRepository;
import org.wcs.myBlog.repository.ImageRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {


    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository, ArticleRepository articleRepository) {
        this.imageRepository = imageRepository;
        this.articleRepository = articleRepository;
    }

    //Mapper
    private ImageDTO convertToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setPath(image.getPath());
        if (image.getArticles() != null ) {
            imageDTO.setArticlesIds(image.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
        }
        return imageDTO;
    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody Image image) {
        Image savedImage = imageRepository.save(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedImage));
    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<Image> images = imageRepository.findAll();
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ImageDTO> imageDTOs = images.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(imageDTOs);
    }

    //ReadOne
    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(image));
    }
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id,@RequestBody Image imageDetails){
        Image updatedImage = imageRepository.findById(id).orElse(null);
        if (updatedImage == null) {
            return ResponseEntity.notFound().build();
        }
        updatedImage.setPath(imageDetails.getPath());

        Image savedImage = imageRepository.save(updatedImage);

        return ResponseEntity.ok(convertToDTO(updatedImage));
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        imageRepository.delete(image);
        return ResponseEntity.noContent().build();
    }
}
