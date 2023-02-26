package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.BraInput;
import com.insane.apiwtb.interfaces.BraRepository;
import com.insane.apiwtb.model.Bra;
import com.insane.apiwtb.services.BraService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/bra")
public class BraController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BraRepository braRepository;
    @Autowired
    private BraService braService;


    @GetMapping
    @ResponseBody
    public List<Bra> braList() {
        return mapList(braRepository.findAll(), Bra.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Bra getBra(@PathVariable Integer id) {
        return braService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Bra braAdd(@RequestBody BraInput braInput) {
        return mapper.map(braService.save(braInput), Bra.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
