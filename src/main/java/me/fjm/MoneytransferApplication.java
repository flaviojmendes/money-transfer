package me.fjm;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneytransferApplication extends Application<MoneytransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MoneytransferApplication().run(args);
    }

    @Override
    public String getName() {
        return "moneytransfer";
    }

    @Override
    public void initialize(final Bootstrap<MoneytransferConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final MoneytransferConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
