package com.rc.runlog.dto;

import lombok.Data;

@Data
public class OpenMeteoResponse {
    private double latitude;
    private double longitude;
    private CurrentWeather current;

}
