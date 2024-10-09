package org.wcs.myBlog.services;

import org.wcs.myBlog.DTO.ImageDTO;
import org.wcs.myBlog.exceptions.RessourceNotFoundException;
import org.wcs.myBlog.mappers.ImageMapper;
import org.wcs.myBlog.models.Image;
import org.wcs.myBlog.repositories.ImageRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    //CRUD
    //Create

    public ImageDTO createImage( Image image) {
        Image savedImage = imageRepository.save(image);
        return imageMapper.convertImageToDTO(savedImage);
    }

    //ReadAll

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        if (images.isEmpty()) {
            return null;
        }
        return images.stream()
                .map(imageMapper::convertImageToDTO)
                .collect(Collectors.toList());
    }

    //ReadOne

    public ImageDTO getImageById( Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(()->
                        new RessourceNotFoundException("L'image avec l'ID"+ id  +"n'a pas été trouvé"));
        if (image == null) {
            return null;
        }
        return imageMapper.convertToDTO(image);
    }
    //Update

    public ImageDTO updateImageById(Long id, Image imageDetails){
        Image updatedImage = imageRepository.findById(id)
                .orElseThrow(()->
                        new RessourceNotFoundException("L'image avec l'ID"+ id  +"n'a pas été trouvé"));
        if (updatedImage == null) {
            return null;

        }
        updatedImage.setPath(imageDetails.getPath());

        Image savedImage = imageRepository.save(updatedImage);

        return imageMapper.convertToDTO(savedImage);
    }

    //Delete

    public boolean deleteImage( Long id) {
        Image image = imageRepository.findById(id).orElseThrow(()->
                new RessourceNotFoundException("L'image avec l'ID"+ id  +"n'a pas été trouvé"));;
        if (image == null) {
            return false;
        }
        imageRepository.delete(image);
        return false;

    }

}
