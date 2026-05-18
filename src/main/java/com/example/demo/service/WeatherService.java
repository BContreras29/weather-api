package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.WeatherDTO;
import com.example.demo.entity.Weather;
import com.example.demo.repository.WeatherRepo;


//This is usually where all the business logic goes for the application
@Service
public class WeatherService {
    //Singleton Pattern - Creational design pattern to create different objects, it creates one instance of a class in the entire JVM 
    private final WeatherRepo repo;

    //Dependency Injection - Technique where Spring frameworks provides the dependencies to a class instead of a class creating those dependencies themselves, part of Invertion of Control (IoC)
    public WeatherService(WeatherRepo repo) {
        this.repo = repo;
    }

    //Optional - Wrapper Class (class where you put another class inside of it, powerful because it allows you to handle null gracefully) it allows you to return a class thats empty which is null, but it handles it for you. 
    //You use optional when it is okay for that piece of information to be invalid , searching for ID when the ID for that entity doesn't exist.
    public Optional<Weather> postWeather(WeatherDTO dto) {
        Weather newWeather = new Weather(
            dto.cityName(),
            dto.region(),
            dto.latitude(),
            dto.longitude(),
            dto.temperature(),
            dto.timestamp()
        );
        //Saves the object we made into the database
        repo.save(newWeather);

        //Wrapping the Weather Object into an optional if its nullable, and returns null if its empty
        return Optional.ofNullable(newWeather);
    }

    //Get by ID
    public Optional<Weather> getByID(Long id) {
        return repo.findById(id);
    }

    //Get multiple weather records using the methods we made in the repo layer
    public List<Weather> getWeatherRecords(String cityName, String region) {
        if(cityName != null && region != null) {
            return repo.findByCityNameIgnoreCaseAndRegionIgnoreCase(cityName, region);
        }

        if(cityName != null) {
            return repo.findByCityNameIgnoreCase(cityName);
        }

        if(region != null) {
            return repo.findByRegionIgnoreCase(region);
        }

        return repo.findAll();
    }

}
