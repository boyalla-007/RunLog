package com.rc.runlog.controller;

import com.rc.runlog.dto.WeatherResponse;
import com.rc.runlog.service.WeatherService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public WeatherResponse getCurrentWeather(@DecimalMin("-90") @DecimalMax("90") @RequestParam double latitude,
                                             @DecimalMin("-180") @DecimalMax("180") @RequestParam double longitude){
        return weatherService.getCurrentWeather(latitude, longitude);
    }
}
