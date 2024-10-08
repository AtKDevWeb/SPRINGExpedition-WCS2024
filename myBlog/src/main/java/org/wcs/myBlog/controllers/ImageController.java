package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ImageDTO;
import org.wcs.myBlog.mappers.ImageMapper;
import org.wcs.myBlog.models.Image;


import org.wcs.myBlog.repositories.ImageRepository;
import org.wcs.myBlog.services.ImageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {



    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(ImageRepository imageRepository,
                           ImageService imageService, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }



    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody Image image) {
        ImageDTO savedImage = imageService.createImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream()
                .map(imageMapper::convertImageToDTO)
                .collect(Collectors.toList());
    }

    //ReadOne
    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        ImageDTO image = imageService.getImageById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(image));
    }
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id,@RequestBody Image imageDetails){
        ImageDTO updatedImage = imageService.updateImageById(id, imageDetails);
        if (updatedImage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedImage);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        if (imageService.deleteImage(id)) {
            return ResponseEntity.noContent().build();
        }ele {
            return ResponseEntity.notFound().build();
        }
    }
}
