package me.fjm.db;

import com.codahale.metrics.health.HealthCheck;
import me.fjm.health.DBHealthCheck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DBHealthCeckTest {

    private final HealthCheck healthyDBHealthCheck = new DBHealthCheck(new AccountRepository(), new ReceiptRepository());
    private final HealthCheck unhealthyDBHealthCheck = new DBHealthCheck(null, null);

    @Test
    public void testHealthyDBHealthCheck() {
        assertTrue(healthyDBHealthCheck.execute().isHealthy());
    }


    @Test
    public void testUnhealthyDBHealthCheck() {
        assertFalse(unhealthyDBHealthCheck.execute().isHealthy());
    }



}
