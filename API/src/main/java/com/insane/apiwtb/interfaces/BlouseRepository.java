package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.Blouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlouseRepository extends JpaRepository<Blouse, Integer> {
}
