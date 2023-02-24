package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.City;
import com.insane.apiwtb.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("SELECT c FROM City c WHERE UPPER( c.state.abbreviation ) LIKE CONCAT('%',UPPER(:stateAbbreviation),'%') AND UPPER( c.state.country.abbreviation) LIKE CONCAT('%',UPPER(:countryAbbreviation),'%')")
    List<City> findAllByCountryAndStateAbbreviation(@Param("countryAbbreviation") String countryAbbreviation, @Param("stateAbbreviation") String stateAbbreviation);

}
