
package com.tuncerergin.covid19.model.covid19api.summary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Summary implements Serializable {

    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("Global")
    @Expose
    public Global global;
    @SerializedName("Countries")
    @Expose
    public List<Country> countries = null;
    @SerializedName("Date")
    @Expose
    public String date;
    private final static long serialVersionUID = -4171303039419465989L;

}
