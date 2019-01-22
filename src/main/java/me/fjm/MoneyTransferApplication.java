package me.fjm;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.fjm.repository.AccountRepository;
import me.fjm.health.DefaultHealthCheck;
import me.fjm.resources.AccountResource;
import me.fjm.resources.TransferResource;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MoneyTransferApplication().run(args);
    }

    @Override
    public String getName() {
        return "moneytransfer";
    }

    @Override
    public void initialize(final Bootstrap<MoneyTransferConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final MoneyTransferConfiguration configuration,
                    final Environment environment) {

        // Account
        final AccountRepository accountRepository = new AccountRepository();
        final AccountResource accountResource = new AccountResource(accountRepository);
        environment.jersey().register(accountResource);


        // Transfer
        final TransferResource transferResource = new TransferResource(accountRepository);
        environment.jersey().register(transferResource);


        // Health Check
        final DefaultHealthCheck healthCheck = new DefaultHealthCheck();
        environment.healthChecks().register("default", healthCheck);

    }

}
