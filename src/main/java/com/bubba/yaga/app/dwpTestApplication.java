package com.bubba.yaga.app;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class dwpTestApplication extends Application<dwpTestConfiguration> {

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
        // TODO: implement application
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
    }

}
