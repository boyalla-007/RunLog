package com.rc.runlog.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RunResponse {
    private String id;
    private LocalDate date;
    private double distance;
    private int duration;
    private double pace;
    private String notes;

}
