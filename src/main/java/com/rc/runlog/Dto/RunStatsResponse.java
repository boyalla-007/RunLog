package com.rc.runlog.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RunStatsResponse {

    private long totalRuns;
    private double totalDistance;
    private double averageDistance;
    private double averagePace;

}
