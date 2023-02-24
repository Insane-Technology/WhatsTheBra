package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.DressInput;
import com.insane.apiwtb.exception.BraTypeNotFoundException;
import com.insane.apiwtb.exception.DressNotFoundException;
import com.insane.apiwtb.exception.ShopNotFoundException;
import com.insane.apiwtb.interfaces.BraTypeRepository;
import com.insane.apiwtb.interfaces.DressRepository;
import com.insane.apiwtb.interfaces.ShopRepository;
import com.insane.apiwtb.model.BraType;
import com.insane.apiwtb.model.Dress;
import com.insane.apiwtb.model.Shop;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DressService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private DressRepository dressRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private BraTypeRepository braTypeRepository;


    @Transactional
    public Dress save(DressInput dressInput) {

        Integer shopId = dressInput.getShop();
        Integer braTypeId = dressInput.getBraType();

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        BraType braType = braTypeRepository.findById(braTypeId)
                .orElseThrow(() -> new BraTypeNotFoundException(braTypeId));

        Dress dress = mapper.map(dressInput, Dress.class);

        dress.setShop(shop);
        dress.setBraType(braType);

        return dressRepository.save(dress);
    }

    public Dress getById(Integer dressId) {
        return dressRepository.findById(dressId)
                .orElseThrow(() -> new DressNotFoundException(dressId));
    }

}
