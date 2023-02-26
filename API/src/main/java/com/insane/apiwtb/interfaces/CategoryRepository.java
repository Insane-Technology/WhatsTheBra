package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
