package com.insane.apiwtb.controller;

import com.insane.apiwtb.dto.BraTypeInput;
import com.insane.apiwtb.interfaces.BraTypeRepository;
import com.insane.apiwtb.model.BraType;
import com.insane.apiwtb.services.BraTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/bra-type")
public class BraTypeCrontroller {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BraTypeRepository braTypeRepository;
    @Autowired
    private BraTypeService braTypeService;

    @GetMapping
    @ResponseBody
    public List<BraType> braTypeList() {
        return mapList(braTypeRepository.findAll(), BraType.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BraType getBraType(@PathVariable Integer id) {
        return braTypeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BraType braTypeAdd(@RequestBody BraTypeInput braTypeInput) {
        return mapper.map(braTypeService.save(braTypeInput), BraType.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
