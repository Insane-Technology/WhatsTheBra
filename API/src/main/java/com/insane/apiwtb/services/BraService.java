package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.BraInput;
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
public class BraService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BraRepository braRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BraTypeRepository braTypeRepository;


    @Transactional
    public Bra save(BraInput braInput) {

        Integer shopId = braInput.getShop();
        Integer braTypeId = braInput.getBraType();

        Bra bra = mapper.map(braInput, Bra.class);

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        BraType braType = braTypeRepository.findById(braTypeId)
                .orElseThrow(() -> new BraTypeNotFoundException(braTypeId));

        List<Category> categoryList = new ArrayList<>();
        for (Integer categoryId : braInput.getCategories()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId));
            categoryList.add(category);
        }

        List<Image> imageList = new ArrayList<>();
        for (Integer imageId : braInput.getImages()) {
            Image image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new ImageNotFoundException(imageId));
            imageList.add(image);
        }

        bra.setShop(shop);
        bra.setBraType(braType);
        bra.setCategories(categoryList);
        bra.setImages(imageList);

        return braRepository.save(bra);
    }

    public Bra getById(Integer braId) {
        return braRepository.findById(braId)
                .orElseThrow(() -> new BraNotFoundException(braId));
    }

}
