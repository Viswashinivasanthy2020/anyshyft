package com.demo.anyshyft.model;

import com.google.gson.annotations.SerializedName;

public class LicenceFormdata {
    @SerializedName("api_key")
    private String api_key;


    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public LicenceFormdata() {
        this.api_key = api_key;
    }
}
