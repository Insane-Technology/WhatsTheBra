package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
