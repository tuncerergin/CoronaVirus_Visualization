package com.tuncerergin.covid19.model;

import lombok.Data;

@Data
public class Location_ {

    private double _long;
    private String countryOrRegion;
    private Object provinceOrState;
    private Object county;
    private String isoCode;
    private double lat;
}
