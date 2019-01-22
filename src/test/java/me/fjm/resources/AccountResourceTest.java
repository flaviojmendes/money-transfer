package me.fjm.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import me.fjm.api.Account;
import me.fjm.db.AccountRepository;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountResourceTest {
    private static AccountRepository repository = new AccountRepository();

    @ClassRule
    public static ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AccountResource(repository))
            .build();

    private final Account account = new Account(1L, "John Doe", 10234.00);


    @Test
    public void testGetAccount() {
        Account retrievedAccount = resources.target("/account")
                .queryParam("id", "1")
                .request().get(Account.class);

        assertNotNull(retrievedAccount);
        assertEquals(account.getId(), retrievedAccount.getId());
        assertEquals(account.getBalance(), retrievedAccount.getBalance());
        assertEquals(account.getName(), retrievedAccount.getName());

    }

    @Test
    public void testGetInvalidAccount() {

        Response response = resources.target("/account")
                .queryParam("id", "100")
                .request().get();
        assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus());

    }



    @Test
    public void testGetAllAccounts() {
        List<Account> retrievedAccounts = resources.target("/account/list")
                .request().get(List.class);

        assertNotNull(retrievedAccounts);
        assertEquals(3, retrievedAccounts.size());

    }


    @Test
    public void testCreateAccount() {

        Account newAccount = new Account();
        newAccount
                .setName("New User")
                .setBalance(30.0);

        Account savedAccount = resources.target("/account")
                .request().post(Entity.json(newAccount), Account.class);

        assertNotNull(savedAccount);
        assertEquals(newAccount.getName(), savedAccount.getName());
        assertEquals(newAccount.getBalance(), savedAccount.getBalance());

        repository.delete(savedAccount.getId());
    }

    @Test
    public void testUpdateAccount() {

        Account newAccount = new Account();
        newAccount.setName("Updated Name");

        Account updatedAccount = resources.target("/account")
                .queryParam("id", "1")
                .request().put(Entity.json(newAccount), Account.class);

        assertNotNull(updatedAccount);
        assertEquals(newAccount.getName(), updatedAccount.getName());
        assertEquals(account.getBalance(), updatedAccount.getBalance());

    }

    @Test
    public void testUpdateInexistentAccount() {

        Account newAccount = new Account();
        newAccount.setName("Updated Name");

        Response response  = resources.target("/account")
                .queryParam("id", "999")
                .request().put(Entity.json(newAccount));

        assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus());

    }

    @Test
    public void testDeleteAccount() {

        Account newAccount = new Account();
        newAccount
                .setName("New User")
                .setBalance(30.0);

        repository.save(newAccount);

        resources.target("/account")
                .queryParam("id", newAccount.getId())
                .request().delete(Account.class);

        Response response = resources.target("/account")
                .queryParam("id", newAccount.getId())
                .request().get();
        assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus());


    }

}
