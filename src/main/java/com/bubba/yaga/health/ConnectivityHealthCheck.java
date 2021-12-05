package com.bubba.yaga.health;

import com.bubba.yaga.config.BptdsApiConfig;
import com.codahale.metrics.health.HealthCheck;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

public class ConnectivityHealthCheck extends HealthCheck {

    private final Client client;
    private final BptdsApiConfig config;

    public ConnectivityHealthCheck(Client client, BptdsApiConfig config) {
        super();
        this.client = client;
        this.config = config;
    }

    @Override
    protected Result check() {

        Response response = client.target(config.getBaseURL()).request().get();

        if (response.getStatus() == 200) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Could not reach BPTDS API");
        }
    }
}
