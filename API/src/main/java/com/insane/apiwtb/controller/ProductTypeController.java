package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.ProductTypeInput;
import com.insane.apiwtb.interfaces.ProductTypeRepository;
import com.insane.apiwtb.model.ProductType;
import com.insane.apiwtb.services.ProductTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/product-type")
public class ProductTypeController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private ProductTypeService productTypeService;


    @GetMapping
    @ResponseBody
    public List<ProductType> productTypes() {
        return mapList(productTypeRepository.findAll(), ProductType.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductType getProductTypeById(@PathVariable Integer id) {
        return productTypeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductType addProductType(@RequestBody ProductTypeInput productTypeInput) {
        return mapper.map(productTypeService.save(productTypeInput), ProductType.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
