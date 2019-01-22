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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReceiptResourceTest {
    private static ReceiptRepository repository = new ReceiptRepository();

    @ClassRule
    public static ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ReceiptResource(repository))
            .build();


    private final Account accountFrom = new Account(1L, "John Doe", 10000.00);
    private final Account accountTo = new Account(2L, "Jimmy Neutron", 234.00);
    private final Receipt receipt = new Receipt(1L, 300.0, accountFrom, accountTo);


    @Test
    public void testGetReceipt() {
        Receipt savedReceipt = repository.save(receipt);

        Receipt retrievedReceipt = resources.target("/receipt/1").request().get(Receipt.class);

        assertNotNull(retrievedReceipt);
        assertEquals(savedReceipt.getId(), retrievedReceipt.getId());
        assertEquals(savedReceipt.getAmount(), retrievedReceipt.getAmount());
        assertEquals(savedReceipt.getFrom().getName(), retrievedReceipt.getFrom().getName());
        assertEquals(savedReceipt.getTo().getName(), retrievedReceipt.getTo().getName());

        repository.delete(retrievedReceipt.getId());
    }

    @Test
    public void testGetInvalidReceipt() {

    Response response = resources.target("/receipt/100").request().get();
    assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus());

    }



    @Test
    public void testGetAllReceipts() {
        Receipt savedReceipt = repository.save(receipt);

        List<Receipt> retrievedReceipts = resources.target("/receipt").request().get(List.class);

        assertNotNull(retrievedReceipts);
        assertEquals(1, retrievedReceipts.size());

        repository.delete(savedReceipt.getId());
    }

}
