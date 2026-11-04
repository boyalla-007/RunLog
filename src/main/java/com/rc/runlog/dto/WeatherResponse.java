package com.rc.runlog.dto;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class WeatherResponse {
    private double temperature;
    private double apparentTemperature;
    private double windSpeed;
}
