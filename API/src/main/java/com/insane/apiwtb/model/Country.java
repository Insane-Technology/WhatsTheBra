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
@Table(name = "TB_COUNTRY")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_country")
    private int id;

    @Column(name = "nm_country", nullable = false, length = 60)
    private String name;

    @Column(name = "ds_abbreviation", length = 2)
    private String abbreviation;

    @Column(name = "ds_iso_alpha3", length = 3)
    private String iso;

    @Column(name = "nr_ddi", length = 3)
    private Integer ddi;

}
