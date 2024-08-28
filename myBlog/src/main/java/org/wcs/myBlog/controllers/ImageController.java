package org.wcs.myBlog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wcs.myBlog.models.Image;
import org.wcs.myBlog.repository.ImageRepository;

@RestController
public class ImageController {

    pirvate finale ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;

    }

    //Maper
//    private ImageDTO convertDTO(){
//
//    }

    //CRUD
    //Create
    @PostMapping
    public ResponseEntity<Image> save(@RequestBody Image image) {

    }

    //ReadAll
    @GetMapping
    public ResponseEntity<List<Image>> findAll() {

    }

    //ReadOne
    @GetMapping("/{id}")

    //Update
    @PostMapping("/{id}")


    //Delete
   @DeleteMapping("/{id}")
}
