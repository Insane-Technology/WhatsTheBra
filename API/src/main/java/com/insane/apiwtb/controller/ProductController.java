package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.ProductInput;
import com.insane.apiwtb.interfaces.ProductRepository;
import com.insane.apiwtb.model.Product;
import com.insane.apiwtb.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/product")
public class ProductController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;


    @GetMapping
    @ResponseBody
    public List<Product> products() {
        return mapList(productRepository.findAll(), Product.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    // TODO CRIAR UM TIPO DTO PARA O REQUESTMAPPING POST RECEBER UMA LISTA INTEIRO PARA FAZER A QUERY DE FILTRO

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductInput productInput) {
        return mapper.map(productService.save(productInput), Product.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
