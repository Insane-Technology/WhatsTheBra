package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.ShopInput;
import com.insane.apiwtb.exception.ShopNotFoundException;
import com.insane.apiwtb.interfaces.*;
import com.insane.apiwtb.model.*;
import com.insane.apiwtb.services.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/shop")
public class ShopController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopService shopService;

    @GetMapping
    @ResponseBody
    public List<Shop> shopList() {
        return mapList(shopRepository.findAll(), Shop.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Shop getShop(@PathVariable Integer id) {
        return shopService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Shop shopAdd(@RequestBody ShopInput shopInput) {
        return mapper.map(shopService.save(shopInput), Shop.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
