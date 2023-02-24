package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.ShopInput;
import com.insane.apiwtb.exception.ShopNotFoundException;
import com.insane.apiwtb.interfaces.ShopRepository;
import com.insane.apiwtb.model.Shop;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ShopRepository shopRepository;


    @Transactional
    public Shop save(ShopInput shopInput) {
        Shop shop = mapper.map(shopInput, Shop.class);
        return shopRepository.save(shop);
    }

    public Shop getById(Integer shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));
    }

}
