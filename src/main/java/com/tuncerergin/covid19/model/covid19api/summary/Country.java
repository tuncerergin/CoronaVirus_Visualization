
package com.tuncerergin.covid19.model.covid19api.summary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {

    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("CountryCode")
    @Expose
    public String countryCode;
    @SerializedName("Slug")
    @Expose
    public String slug;
    @SerializedName("NewConfirmed")
    @Expose
    public int newConfirmed;
    @SerializedName("TotalConfirmed")
    @Expose
    public int totalConfirmed;
    @SerializedName("NewDeaths")
    @Expose
    public int newDeaths;
    @SerializedName("TotalDeaths")
    @Expose
    public int totalDeaths;
    @SerializedName("NewRecovered")
    @Expose
    public int newRecovered;
    @SerializedName("TotalRecovered")
    @Expose
    public int totalRecovered;
    @SerializedName("Date")
    @Expose
    public String date;
    @SerializedName("Premium")
    @Expose
    public Premium premium;
    private final static long serialVersionUID = -1645142075745438029L;

}
