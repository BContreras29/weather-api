package com.example.demo.dto;


//DTO - Data Transfer Object, way to move data in software, from application to frontend or to/from database, hides sensitive data, brings stability, representation of the Entity class because you don't want to be able to change the entity or see sensitive info
//Record - Special class that is used for declaring immutable data, used to carry data, generates default methods (getters, default constructor), private final class (cannot be changed maliciously).
//It is immutable because this is a request for a change to an object, so the request has already been made, there is no need to make further changes.

public record WeatherDTO(
    String cityName,
    String region,
    Double latitude,
    Double longitude,
    Double temperature,
    Long timestamp
) {}    

