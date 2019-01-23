package me.fjm.db;

import com.codahale.metrics.health.HealthCheck;
import me.fjm.health.DBHealthCheck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DBHealthCeckTest {

    @Test
    public void testHealthyDBHealthCheck() {
        HealthCheck healthyDBHealthCheck = new DBHealthCheck(new AccountRepository(), new ReceiptRepository());
        assertTrue(healthyDBHealthCheck.execute().isHealthy());
    }


    @Test
    public void testUnhealthyAccountRepositoryDBHealthCheck() {
        HealthCheck unhealthyDBHealthCheck = new DBHealthCheck(null, new ReceiptRepository());
        assertFalse(unhealthyDBHealthCheck.execute().isHealthy());
    }

    @Test
    public void testUnhealthyReceiptRepositoryDBHealthCheck() {
        HealthCheck unhealthyDBHealthCheck = new DBHealthCheck(new AccountRepository(), null);
        assertFalse(unhealthyDBHealthCheck.execute().isHealthy());
    }



}
