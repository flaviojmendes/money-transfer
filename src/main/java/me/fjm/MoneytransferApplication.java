package me.fjm;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.fjm.core.AccountRepository;
import me.fjm.health.DefaultHealthCheck;
import me.fjm.resources.AccountResource;

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

        // Account
        final AccountRepository accountRepository = new AccountRepository();
        final AccountResource accountResource = new AccountResource(accountRepository);
        environment.jersey().register(accountResource);


        // Health Check
        final DefaultHealthCheck healthCheck = new DefaultHealthCheck();
        environment.healthChecks().register("default", healthCheck);

    }

}
