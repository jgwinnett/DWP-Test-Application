package com.bubba.yaga.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BptdsApiConfig {

    private String baseURL;

    public BptdsApiConfig() {}

    @JsonProperty
    public String getBaseURL() {return this.baseURL; }

    @JsonProperty
    public void setBaseURL(String baseURL) {this.baseURL = baseURL; }
}
