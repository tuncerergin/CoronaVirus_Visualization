package com.tuncerergin.covid19.controller;

import com.tuncerergin.covid19.model.CoronaVirus;
import com.tuncerergin.covid19.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CovidRestController {
    @Autowired
    private RestService restService;

    @GetMapping("/covidCountry/{countryCode}")
    public CoronaVirus getData(@PathVariable String countryCode) {
        return restService.getData(countryCode);
    }
}
