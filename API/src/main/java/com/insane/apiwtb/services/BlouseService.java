package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.BlouseInput;
import com.insane.apiwtb.exception.*;
import com.insane.apiwtb.interfaces.*;
import com.insane.apiwtb.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlouseService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BlouseRepository blouseRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BraTypeRepository braTypeRepository;


    @Transactional
    public Blouse save(BlouseInput blouseInput) {

        Integer shopId = blouseInput.getShop();
        Integer braTypeId = blouseInput.getBraType();

        Blouse blouse = mapper.map(blouseInput, Blouse.class);

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        BraType braType = braTypeRepository.findById(braTypeId)
                .orElseThrow(() -> new BraTypeNotFoundException(braTypeId));

        List<Category> categoryList = new ArrayList<>();
        for (Integer categoryId : blouseInput.getCategories()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId));
            categoryList.add(category);
        }

        List<Image> imageList = new ArrayList<>();
        for (Integer imageId : blouseInput.getImages()) {
            Image image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new ImageNotFoundException(imageId));
            imageList.add(image);
        }

        blouse.setShop(shop);
        blouse.setBraType(braType);
        blouse.setCategories(categoryList);
        blouse.setImages(imageList);

        return blouseRepository.save(blouse);
    }

    public Blouse getById(Integer blouseId) {
        return blouseRepository.findById(blouseId)
                .orElseThrow(() -> new BlouseNotFoundException(blouseId));
    }

}
