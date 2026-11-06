package com.rc.runlog;
import com.rc.runlog.dto.WeatherResponse;
import com.rc.runlog.service.WeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RunLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunLogApplication.class, args);
    }
}
