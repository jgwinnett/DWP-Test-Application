package com.bubba.yaga.app;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // TODO: application initialization
    }

    @Override
    public void run(final dwpTestConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
