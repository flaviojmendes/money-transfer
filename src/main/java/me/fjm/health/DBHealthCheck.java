package me.fjm.health;

import com.codahale.metrics.health.HealthCheck;
import me.fjm.db.AccountRepository;
import me.fjm.db.ReceiptRepository;

/**
 * As there is no Real Database this Health Check
 * exemplify what can be done in order to ensure
 * the Database is accessible.
 */
public class DBHealthCheck extends HealthCheck {

    private ReceiptRepository receiptRepository;
    private AccountRepository accountRepository;

    public DBHealthCheck(AccountRepository accountRepository, ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    protected Result check() throws Exception {
        if(accountRepository != null
                && receiptRepository != null) {

            return Result.healthy();
        }
        return Result.unhealthy("The DB is not accessible.");
    }
}
