package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WeatherDTO;
import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;


//This layer is where other microservices or other application interact with this app.
//Here is where we use the HTTP commands
//This is the REST API. Maps everything to actually send and receive as a JSON file
//Everything in this class already starts with "/weather" as an endpoint
@RestController
@RequestMapping("/weather")
public class WeatherController {

    //Singleton instance of the class
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    //JSON Response, this is a response that turns into a JSON
    @PostMapping
    public ResponseEntity<Weather> postWeather(@RequestBody WeatherDTO dto) {
        Optional<Weather> opWeather = service.postWeather(dto);

        if(opWeather.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        //opWeather.get() gets the actual object from the Optional wrapper
        return ResponseEntity.created(URI.create("/weather/" + opWeather.get().getId())).body(opWeather.get());
    }


    // /weather/ID
    @GetMapping("/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable Long id) {
        Optional<Weather> opWeather = service.getByID(id);

        if(opWeather.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opWeather.get());
    }

    // /weather?cityName=cityName&region=region, for query parameters, we use @RequestParam
    @GetMapping
    public ResponseEntity<List<Weather>> getWeatherRecords(
        @RequestParam(required = false) String cityName, 
        @RequestParam(required = false) String region) {

            List<Weather> weatherRecords = service.getWeatherRecords(cityName, region);
            
            return ResponseEntity.ok(weatherRecords);
    }

    // /weather/{id}, for PUT, PATCH and DELETE
    @RequestMapping(value = "{id}", method = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<Void> methodNotAllowed() {
        return ResponseEntity.status(405).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putWeather(@RequestBody WeatherDTO dto) {
        return ResponseEntity.status(405).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchWeather(@RequestBody WeatherDTO dto) {
        return ResponseEntity.status(405).build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeather(@RequestBody WeatherDTO dto) {
        return ResponseEntity.status(405).build();
    }
}
