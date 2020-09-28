
package com.tuncerergin.covid19.model.covid19api.summary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Global implements Serializable {

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
    private final static long serialVersionUID = -978577307726318536L;

}
