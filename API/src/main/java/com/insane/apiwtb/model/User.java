package com.insane.apiwtb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @Column(name = "nm_user", nullable = false, length = 50)
    private String name;

    @Column(name = "ds_email", length = 80)
    private String email;

    @Column(name = "ds_pass", length = 64)
    private String pass;

    @Column(name = "dt_nasc")
    private Date birthday;

    @Column(name = "dt_created")
    private Date created;

    @Column(name = "dt_updated")
    private Date updated;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @ManyToOne
    @JoinColumn(name = "id_image")
    private Image image;

    @ManyToMany
    @JoinTable(name = "tb_favourite_product", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_product"))
    private List<Product> favouriteProducts  = new ArrayList<>();

}
