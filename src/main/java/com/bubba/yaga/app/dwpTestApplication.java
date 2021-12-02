package com.bubba.yaga.app;

import com.bubba.yaga.config.dwpTestConfiguration;
import com.bubba.yaga.gateway.BptdsGateway;
import com.bubba.yaga.health.ConnectivityHealthCheck;
import com.bubba.yaga.resources.UserInOrNearLondonResource;
import com.bubba.yaga.service.UserInOrNearLondonService;
import com.bubba.yaga.service.FilterUsersByProximityService;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

public class dwpTestApplication extends Application<dwpTestConfiguration> {

    private final static Logger LOGGER = LoggerFactory.getLogger(dwpTestApplication.class);

    public static void main(final String[] args) throws Exception {
        new dwpTestApplication().run(args);
    }

    @Override
    public String getName() {
        return "dwpTest";
    }

    @Override
    public void initialize(final Bootstrap<dwpTestConfiguration> bootstrap) {

    }

    @Override
    public void run(final dwpTestConfiguration configuration,
                    final Environment environment) {

        registerResources(configuration, environment);
    }

    public void registerResources(final dwpTestConfiguration configuration,
                                  final Environment environment) {

        Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build(getName());

        BptdsGateway gateway = new BptdsGateway(client, configuration.getBptdsApiConfig(), environment.getObjectMapper());
        FilterUsersByProximityService filterUsersByProximityService = new FilterUsersByProximityService();
        UserInOrNearLondonService userInOrNearLondonService = new UserInOrNearLondonService(gateway,filterUsersByProximityService);

        environment.jersey().register(new UserInOrNearLondonResource(userInOrNearLondonService));
        environment.healthChecks().register("downstream_api", new ConnectivityHealthCheck(client, configuration.getBptdsApiConfig()));
    }

}
