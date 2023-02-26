package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
