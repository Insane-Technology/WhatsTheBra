package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.ShopInput;
import com.insane.apiwtb.interfaces.DressRepository;
import com.insane.apiwtb.interfaces.ShopRepository;
import com.insane.apiwtb.model.Dress;
import com.insane.apiwtb.model.Shop;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1")
public class ApiController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private DressRepository dressRepository;

    @GetMapping("/dress")
    @ResponseBody
    public List<Dress> dressList() {
        List<Dress> dresses = dressRepository.findAll();
        return mapList(dresses, Dress.class);
    }

    @PostMapping("/shop")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Shop shopAdd(@RequestBody ShopInput shopInput) {
        Shop shop = mapper.map(shopInput, Shop.class);
        return shopRepository.save(shop);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
