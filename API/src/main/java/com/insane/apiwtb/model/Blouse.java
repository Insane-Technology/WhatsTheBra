package com.insane.apiwtb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TB_BLOUSE")
public class Blouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_blouse")
    private int id;

    @Column(name = "nm_blouse", nullable = false, length = 50)
    private String name;

    @Column(name = "nr_price", precision = 9, scale = 2)
    private Double price;

    @Column(name = "ds_description", length = 200)
    private String description;

    @Column(name = "nr_discount", precision = 3)
    private Integer discount;

    @ManyToOne
    @JoinColumn(name = "id_shop")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "id_bra_type")
    private BraType braType;

    @ManyToMany
    @JoinTable(name = "tb_category_blouse", joinColumns = @JoinColumn(name = "id_blouse"), inverseJoinColumns = @JoinColumn(name = "id_category"))
    private List<Category> categories  = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tb_image_blouse", joinColumns = @JoinColumn(name = "id_blouse"), inverseJoinColumns = @JoinColumn(name = "id_image"))
    private List<Image> images = new ArrayList<>();

}
