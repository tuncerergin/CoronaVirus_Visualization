package com.tuncerergin.covid19.model;

import lombok.Data;

@Data
public class MonthlyTotalCases {
    String monthName;
    int totalCases;
    int totalRecovered;
    int totalDeath;
}
