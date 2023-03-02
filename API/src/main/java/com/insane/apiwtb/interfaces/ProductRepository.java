package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // TODO IMPLEMENTAR OS FILTROS COM QUERIES CUSTOMIZADAS
    // TODO QUERY PARAM CATEGORY e BRATYPES

}
