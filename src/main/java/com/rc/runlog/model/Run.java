package com.rc.runlog.model;



import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "runs")
public class Run {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private LocalDate date;
    private double distance;
    private int duration;
    private double pace;
    private String notes;
}
