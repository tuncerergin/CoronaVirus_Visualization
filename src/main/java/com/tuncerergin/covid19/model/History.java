package com.tuncerergin.covid19.model;

import lombok.Data;

@Data
public class History {

    private String date;
    private int confirmed;
    private int deaths;
    private int recovered;
}
