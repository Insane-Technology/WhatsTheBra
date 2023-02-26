package com.insane.apiwtb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageOut {

    private Integer id;
    private String name;
    private String imageURL;
    private String message;

}