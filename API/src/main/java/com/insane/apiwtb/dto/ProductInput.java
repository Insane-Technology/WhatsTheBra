package com.insane.apiwtb.dto;

import lombok.Data;

import java.util.List;

@Data
public abstract class ProductInput {

    private String name;
    private Double price;
    private String description;
    private Integer discount;
    private Integer shop;
    private Integer braType;
    private List<Integer> categories;
    private List<Integer> images;

}
