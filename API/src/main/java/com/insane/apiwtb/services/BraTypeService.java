package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.BraTypeInput;
import com.insane.apiwtb.exception.BraTypeNotFoundException;
import com.insane.apiwtb.interfaces.BraTypeRepository;
import com.insane.apiwtb.model.BraType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BraTypeService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BraTypeRepository braTypeRepository;


    @Transactional
    public BraType save(BraTypeInput braTypeInput) {
        BraType braType = mapper.map(braTypeInput, BraType.class);
        return braTypeRepository.save(braType);
    }

    public BraType getById(Integer braTypeId) {
        return braTypeRepository.findById(braTypeId)
                .orElseThrow(() -> new BraTypeNotFoundException(braTypeId));
    }

}
