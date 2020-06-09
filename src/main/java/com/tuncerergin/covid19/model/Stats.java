package com.tuncerergin.covid19.model;

import lombok.Data;
import java.util.List;

@Data
public class Stats {

    private int totalConfirmedCases;
    private int newlyConfirmedCases;
    private int totalDeaths;
    private int newDeaths;
    private int totalRecoveredCases;
    private int newlyRecoveredCases;
    private List<History> history = null;
    private List<Breakdown> breakdowns = null;

}
