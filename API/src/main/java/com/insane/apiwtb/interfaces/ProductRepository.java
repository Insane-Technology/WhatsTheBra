package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
