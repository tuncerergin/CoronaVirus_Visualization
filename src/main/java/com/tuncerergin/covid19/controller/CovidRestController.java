package com.tuncerergin.covid19.controller;

import com.tuncerergin.covid19.model.CoronaVirus;
import com.tuncerergin.covid19.model.History;
import com.tuncerergin.covid19.service.RestService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class CovidRestController {
    private final RestService restService;

    public CovidRestController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/covidCountry/{countryCode}")
    public CoronaVirus getData(@PathVariable String countryCode) {
        return restService.getData(countryCode);
    }

    @GetMapping("/covidData/")
    public CoronaVirus getDataByMonthly(@RequestParam(value = "countryCode", required = false, defaultValue = "global") String countryCode,
                                        @RequestParam(value = "dateBegin", required = false) String dateBegin,
                                        @RequestParam(value = "dateEnd", required = false) String dateEnd) {
        CoronaVirus coronaVirus = restService.getData(countryCode);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate beginDate = null;
        LocalDate endDate = null;
        if (dateBegin != null) {
            beginDate = LocalDate.parse(dateBegin, inputFormatter).minusDays(1);
        }
        if (dateEnd != null) {
            endDate = LocalDate.parse(dateEnd, inputFormatter).plusDays(1);
        }

        List<History> data = new ArrayList<>();
        for (History history : coronaVirus.getStats().getHistory()) {
            LocalDate date = LocalDate.parse(history.getDate() + ".000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH));
            if (beginDate != null & endDate != null) {
                if (date.isAfter(beginDate) && date.isBefore(endDate)) {
                    data.add(history);
                }
            } else if (beginDate != null) {
                if (date.isAfter(beginDate)) {
                    data.add(history);
                }
            } else if (endDate != null) {
                if (date.isBefore(endDate)) {
                    data.add(history);
                }
            } else {
                data.add(history);
            }
        }
        coronaVirus.getStats().setHistory(data);

        return coronaVirus;
    }
}
