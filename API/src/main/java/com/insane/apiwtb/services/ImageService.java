package com.insane.apiwtb.services;

import com.insane.apiwtb.exception.ImageNotFoundException;
import com.insane.apiwtb.interfaces.ImageRepository;
import com.insane.apiwtb.model.Image;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ImageRepository imageRepository;

    public Image getById(Integer imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException(imageId));
    }

}
