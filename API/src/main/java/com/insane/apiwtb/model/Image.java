package com.insane.apiwtb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TB_IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private int id;

    @Column(name = "nm_image", nullable = false, length = 50)
    private String name;

    @Column(name = "dt_created")
    private Date created;

    @Column(name = "dt_updated")
    private Date updated;

}
