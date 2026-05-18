package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Weather;

//Interface that extends another interface, allows to create different query methods without having to write them out using a standardized syntax.
//This is basically where the queries for the database go.
@Repository
public interface WeatherRepo extends JpaRepository<Weather, Long>{
    public List<Weather> findByCityNameIgnoreCase(String cityName);
    public List<Weather> findByRegionIgnoreCase(String region);
    public List<Weather> findByCityNameIgnoreCaseAndRegionIgnoreCase(String cityName, String region);
}
