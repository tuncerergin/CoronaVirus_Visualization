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
    private final String subscriptionKey;

    @Autowired
    public RestServiceImpl(
            RestTemplate theRestTemplate,
            @Value("https://api.smartable.ai/coronavirus/stats/") String theUrl, @Value("${restApi.subscriptionKey}") String subscriptionKey) {
        restTemplate = theRestTemplate;
        url = theUrl;
        this.subscriptionKey = subscriptionKey;
    }

    @Override
    public CoronaVirus getData(String countryCode) {
        responseHeaders = new HttpHeaders();
        responseHeaders.set("Subscription-Key",
                subscriptionKey);
        request = new HttpEntity(responseHeaders);

        ResponseEntity<CoronaVirus> responseEntity =
                restTemplate.exchange(url + countryCode,
                        HttpMethod.GET, request,
                        new ParameterizedTypeReference<CoronaVirus>() {
                        });
        return responseEntity.getBody();
    }

}
