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
@Table(name = "TB_STATE")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_state")
    private int id;

    @Column(name = "nm_state", nullable = false, length = 60)
    private String name;

    @Column(name = "ds_abbreviation", nullable = false, length = 3)
    private String abbreviation;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

}
