package com.insane.apiwtb.dto;

import lombok.Data;

@Data
public class DressInput {

    private String name;
    private Double price;
    private String description;
    private Integer discount;
    private Integer shop;
    private Integer braType;

}
