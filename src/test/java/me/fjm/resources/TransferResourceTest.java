package me.fjm.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import me.fjm.api.Account;
import me.fjm.api.Receipt;
import me.fjm.db.AccountRepository;
import me.fjm.db.ReceiptRepository;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransferResourceTest {
    private static ReceiptRepository receiptRepository = new ReceiptRepository();
    private static AccountRepository accountRepository = new AccountRepository();

    @ClassRule
    public static ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TransferResource(accountRepository, receiptRepository))
            .build();



    @Test
    public void testTransferBetweenAccounts() {

        Receipt generatedReceipt = resources.target("/transfer")
                .queryParam("from", "1")
                .queryParam("to", "2")
                .queryParam("amount", "100.10").request()
                .post(null, Receipt.class);

        assertNotNull(generatedReceipt);
        assertEquals(new Long(1), generatedReceipt.getId());
        assertEquals(new Long(1), generatedReceipt.getFrom().getId());
        assertEquals(new Long(2), generatedReceipt.getTo().getId());


    }

    @Test
    public void testTransferBetweenInvalidAccounts() {

        Response response = resources.target("/transfer")
                .queryParam("from", "10")
                .queryParam("to", "2")
                .queryParam("amount", "999999999").request()
                .post(null);

        assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus());
    }

    @Test
    public void testTransferBetweenSameAccount() {

        Response response = resources.target("/transfer")
                .queryParam("from", "2")
                .queryParam("to", "2")
                .queryParam("amount", "999999999").request()
                .post(null);

        assertEquals(HttpStatus.FORBIDDEN_403, response.getStatus());
    }

    @Test
    public void testTransferBetweenAccountsWithInsufficientFunds() {

        Response response = resources.target("/transfer")
                .queryParam("from", "1")
                .queryParam("to", "2")
                .queryParam("amount", "999999999").request()
                .post(null);

        assertEquals(HttpStatus.FORBIDDEN_403, response.getStatus());
    }


}
