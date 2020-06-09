package com.tuncerergin.covid19.service;

import com.tuncerergin.covid19.model.CoronaVirus;

public interface RestService {
    CoronaVirus getData(String countryCode);
}
