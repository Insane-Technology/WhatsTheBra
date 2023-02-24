package com.insane.apiwtb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TB_DRESS")
public class Dress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dress")
    private int id;

    @Column(name = "nm_dress", nullable = false, length = 50)
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

}