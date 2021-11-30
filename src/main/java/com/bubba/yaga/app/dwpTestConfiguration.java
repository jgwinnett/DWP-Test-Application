package com.bubba.yaga.app;

import com.bubba.yaga.config.BptdsApiConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class dwpTestConfiguration extends Configuration {

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
        this.jerseyClient = jerseyClient;
    }

    @Valid
    @NotNull
    private BptdsApiConfig bptdsApiConfig = new BptdsApiConfig();

    @JsonProperty("bptdsApiConfig")
    public BptdsApiConfig getBptdsApiConfig() {
        return bptdsApiConfig;
    }

    @JsonProperty("bptdsApiConfig")
    public void setBptdsApiConfig(BptdsApiConfig bptdsApiConfig) {
        this.bptdsApiConfig = bptdsApiConfig;
    }
}
