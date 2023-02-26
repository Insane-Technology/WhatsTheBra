package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.CategoryInput;
import com.insane.apiwtb.exception.CategoryNotFoundException;
import com.insane.apiwtb.interfaces.CategoryRepository;
import com.insane.apiwtb.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional
    public Category save(CategoryInput categoryInput) {
        Category category = mapper.map(categoryInput, Category.class);
        return categoryRepository.save(category);
    }

    public Category getById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

}
