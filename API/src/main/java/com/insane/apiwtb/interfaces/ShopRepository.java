package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
}
