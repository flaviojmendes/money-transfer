package me.fjm.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import me.fjm.api.Account;
import me.fjm.api.Receipt;
import me.fjm.db.AccountRepository;
import me.fjm.db.ReceiptRepository;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
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


        Receipt generatedReceipt = resources.target("/transfer").request()
                .property("from", "1")
                .property("to", "2")
                .property("amount", "100.10")
                .post(null,Receipt.class);


        assertNotNull(generatedReceipt);
        assertEquals(new Long(1), generatedReceipt.getId());
        assertEquals(new Long(1), generatedReceipt.getFrom().getId());
        assertEquals(new Long(2), generatedReceipt.getTo().getId());


    }


}
