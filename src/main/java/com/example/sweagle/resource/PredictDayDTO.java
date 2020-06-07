package com.example.sweagle.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Estimation return value for the day.
 */
@Data
@AllArgsConstructor
public class PredictDayDTO {
    private Long msgCount;

    private int dayOfWeek;
}
