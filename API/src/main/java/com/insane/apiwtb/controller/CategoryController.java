package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.CategoryInput;
import com.insane.apiwtb.interfaces.CategoryRepository;
import com.insane.apiwtb.model.Category;
import com.insane.apiwtb.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/category")
public class CategoryController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;


    @GetMapping
    @ResponseBody
    public List<Category> categoryList() {
        return mapList(categoryRepository.findAll(), Category.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Category getCategoryById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Category categoryAdd(@RequestBody CategoryInput categoryInput) {
        return mapper.map(categoryService.save(categoryInput), Category.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
