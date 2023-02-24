package com.insane.apiwtb.controller;

import com.insane.apiwtb.interfaces.*;
import com.insane.apiwtb.model.City;
import com.insane.apiwtb.model.Country;
import com.insane.apiwtb.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/location")
public class LocationController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;


    @GetMapping("/country")
    @ResponseBody
    public List<Country> countryList() {
        List<Country> countries = countryRepository.findAll();
        return mapList(countries, Country.class);
    }

    @GetMapping("/state")
    @ResponseBody
    public List<State> stateList() {
        List<State> states = stateRepository.findAll();
        return mapList(states, State.class);
    }

    @GetMapping("/city")
    @ResponseBody
    public List<City> cityList() {
        List<City> cities = cityRepository.findAll();
        return mapList(cities, City.class);
    }

    @GetMapping("/{country}/state")
    @ResponseBody
    public List<State> stateListByCountry(@PathVariable String country) {
        List<State> states = stateRepository.findAllByCountryAbbreviation(country);
        return mapList(states, State.class);
    }

    @GetMapping("/{country}/{state}/city")
    @ResponseBody
    public List<City> cityListByState(@PathVariable String country, @PathVariable String state) {
        List<City> cities = cityRepository.findAllByCountryAndStateAbbreviation(country, state);
        return mapList(cities, City.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
