package com.tuncerergin.covid19.service;

import com.tuncerergin.covid19.model.turkey.CovidData;

public interface TurkeyRestService {
    CovidData[] getData(String query);
}
