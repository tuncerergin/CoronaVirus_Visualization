package com.tuncerergin.covid19.service;

import com.tuncerergin.covid19.model.turkey.CovidData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TurkeyRestServiceImpl implements TurkeyRestService {

    private final RestTemplate restTemplate;
    private final String url;
    HttpHeaders responseHeaders;
    HttpEntity request;

    @Autowired
    public TurkeyRestServiceImpl(
            RestTemplate restTemplate,
            @Value("${turkey.covid19api}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public CovidData[] getData(String query) {
        ResponseEntity<CovidData[]> responseEntity =
                restTemplate.exchange(url + query,
                        HttpMethod.GET, request,
                        new ParameterizedTypeReference<CovidData[]>() {
                        });
        return responseEntity.getBody();
    }
}
