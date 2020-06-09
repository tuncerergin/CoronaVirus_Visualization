package com.tuncerergin.covid19.model;

import lombok.Data;

@Data
public class Breakdown {

    private Location_ location;
    private int totalConfirmedCases;
    private int newlyConfirmedCases;
    private int totalDeaths;
    private int newDeaths;
    private int totalRecoveredCases;
    private int newlyRecoveredCases;
}
