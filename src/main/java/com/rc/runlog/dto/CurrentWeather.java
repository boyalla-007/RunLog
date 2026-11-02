package com.rc.runlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentWeather {

    private String time;

    @JsonProperty("temperature_2m")
    private double temperature2m;

    @JsonProperty("apparent_temperature")
    private double apparentTemperature;

    @JsonProperty("wind_speed_10m")
    private double windSpeed10m;
}
