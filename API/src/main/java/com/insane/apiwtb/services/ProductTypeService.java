package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.ProductTypeInput;
import com.insane.apiwtb.exception.BraTypeNotFoundException;
import com.insane.apiwtb.interfaces.ProductTypeRepository;
import com.insane.apiwtb.model.ProductType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductTypeService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductTypeRepository productTypeRepository;


    @Transactional
    public ProductType save(ProductTypeInput productTypeInput) {
        ProductType productType = mapper.map(productTypeInput, ProductType.class);
        return productTypeRepository.save(productType);
    }

    public ProductType getById(Integer productTypeId) {
        return productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new BraTypeNotFoundException(productTypeId));
    }

}
