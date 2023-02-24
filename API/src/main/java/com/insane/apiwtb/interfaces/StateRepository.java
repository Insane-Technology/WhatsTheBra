package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {

    @Query("SELECT s FROM State s WHERE UPPER( s.country.abbreviation ) LIKE CONCAT('%',UPPER(:countryAbbreviation),'%')")
    List<State> findAllByCountryAbbreviation(@Param("countryAbbreviation") String countryAbbreviation);

}
