package com.tuncerergin.covid19.service;

import com.tuncerergin.covid19.model.CoronaVirus;
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
public class RestServiceImpl implements RestService {

    private final RestTemplate restTemplate;
    private final String url;
    HttpHeaders responseHeaders;
    HttpEntity request;
    private final String key;
    private final String host;

    @Autowired
    public RestServiceImpl(
            RestTemplate theRestTemplate,
            @Value("${restApi.url}") String theUrl,
            @Value("${restApi.key}") String key,
            @Value("${restApi.host}") String host) {
        restTemplate = theRestTemplate;
        this.url = theUrl;
        this.key = key;
        this.host = host;
    }

    @Override
    public CoronaVirus getData(String countryCode) {
        responseHeaders = new HttpHeaders();
        responseHeaders.set("x-rapidapi-host", host);
        responseHeaders.set("x-rapidapi-key", key);

        request = new HttpEntity(responseHeaders);

        ResponseEntity<CoronaVirus> responseEntity =
                restTemplate.exchange(url + countryCode + "/",
                        HttpMethod.GET, request,
                        new ParameterizedTypeReference<CoronaVirus>() {
                        });
        return responseEntity.getBody();
    }

}
