package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
}
