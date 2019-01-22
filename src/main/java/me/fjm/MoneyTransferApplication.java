package me.fjm;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.fjm.db.AccountRepository;
import me.fjm.health.DBHealthCheck;
import me.fjm.db.ReceiptRepository;
import me.fjm.resources.AccountResource;
import me.fjm.resources.ReceiptResource;
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
    public void initialize(final Bootstrap<MoneyTransferConfiguration> bootstrap) {}

    @Override
    public void run(final MoneyTransferConfiguration configuration,
                    final Environment environment) {

        // Account
        final AccountRepository accountRepository = new AccountRepository();
        final AccountResource accountResource = new AccountResource(accountRepository);
        environment.jersey().register(accountResource);


        // Receipt
        final ReceiptRepository receiptRepository = new ReceiptRepository();
        final ReceiptResource receiptResource = new ReceiptResource(receiptRepository);
        environment.jersey().register(receiptResource);


        // Transfer
        final TransferResource transferResource = new TransferResource(accountRepository, receiptRepository);
        environment.jersey().register(transferResource);


        // Health Check
        final DBHealthCheck healthCheck = new DBHealthCheck(accountRepository, receiptRepository);
        environment.healthChecks().register("default", healthCheck);

    }

}
