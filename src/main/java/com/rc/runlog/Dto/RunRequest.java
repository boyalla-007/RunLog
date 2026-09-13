package com.rc.runlog.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RunRequest {
    @NotNull(message = "Date cannot be NULL")
    private LocalDate date;
    @Positive(message = "Distance has to be greater than 0")
    private double distance;
    @Positive(message = "Duration has to be greater than 0")
    private int duration;
    private String notes;
}
