package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.BlouseInput;
import com.insane.apiwtb.interfaces.BlouseRepository;
import com.insane.apiwtb.model.Blouse;
import com.insane.apiwtb.services.BlouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/blouse")
public class BlouseController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BlouseRepository blouseRepository;
    @Autowired
    private BlouseService blouseService;


    @GetMapping
    @ResponseBody
    public List<Blouse> blouseList() {
        return mapList(blouseRepository.findAll(), Blouse.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Blouse getBlouse(@PathVariable Integer id) {
        return blouseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Blouse blouseAdd(@RequestBody BlouseInput blouseInput) {
        return mapper.map(blouseService.save(blouseInput), Blouse.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
