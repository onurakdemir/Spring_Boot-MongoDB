package com.example.sweagle.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Estimation return value for the week.
 */
@Data
@AllArgsConstructor
public class PredictWeekDTO {
    private Long msgCount;

    private int weekOfYear;
}
