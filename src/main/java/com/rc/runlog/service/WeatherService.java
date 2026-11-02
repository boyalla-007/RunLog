package com.rc.runlog.service;

import com.rc.runlog.dto.OpenMeteoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {
    private final RestClient restClient;

    public WeatherService(RestClient restClient) {
        this.restClient = restClient;
    }

    public OpenMeteoResponse getCurrentWeather(){

        return restClient.get()
                .uri(uriBuilder ->uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude",13.34)
                        .queryParam("longitude",74.74)
                        .queryParam("current","temperature_2m,apparent_temperature,wind_speed_10m")
                        .build())
                .retrieve()
                .body(OpenMeteoResponse.class);
    }
}
