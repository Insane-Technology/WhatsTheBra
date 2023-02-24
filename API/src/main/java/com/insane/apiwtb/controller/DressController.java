package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.DressInput;
import com.insane.apiwtb.interfaces.DressRepository;
import com.insane.apiwtb.model.Dress;
import com.insane.apiwtb.services.DressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/dress")
public class DressController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private DressRepository dressRepository;
    @Autowired
    private DressService dressService;


    @GetMapping
    @ResponseBody
    public List<Dress> dressList() {
        return mapList(dressRepository.findAll(), Dress.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Dress getDress(@PathVariable Integer id) {
        return dressService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Dress dressAdd(@RequestBody DressInput dressInput) {
        return mapper.map(dressService.save(dressInput), Dress.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
