package org.wcs.myBlog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.DTO.ImageDTO;
import org.wcs.myBlog.models.Image;

import org.wcs.myBlog.services.ImageService;

import java.util.List;


@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
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
        List<ImageDTO> images = imageService.getAllImages();
        if (images == null || images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(images);
    }

    //ReadOne
    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        ImageDTO image = imageService.getImageById(id);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(image);
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
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
