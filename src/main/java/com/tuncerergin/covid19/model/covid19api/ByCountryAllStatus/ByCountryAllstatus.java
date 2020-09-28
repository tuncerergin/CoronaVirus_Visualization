
package com.tuncerergin.covid19.model.covid19api.ByCountryAllStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ByCountryAllstatus implements Serializable {

    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("CountryCode")
    @Expose
    public String countryCode;
    @SerializedName("Province")
    @Expose
    public String province;
    @SerializedName("City")
    @Expose
    public String city;
    @SerializedName("CityCode")
    @Expose
    public String cityCode;
    @SerializedName("Lat")
    @Expose
    public String lat;
    @SerializedName("Lon")
    @Expose
    public String lon;
    @SerializedName("Confirmed")
    @Expose
    public int confirmed;
    @SerializedName("Deaths")
    @Expose
    public int deaths;
    @SerializedName("Recovered")
    @Expose
    public int recovered;
    @SerializedName("Active")
    @Expose
    public int active;
    @SerializedName("Date")
    @Expose
    public String date;
    private final static long serialVersionUID = 8313882631696991649L;

}
