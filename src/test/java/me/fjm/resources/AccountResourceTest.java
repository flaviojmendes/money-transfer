package me.fjm.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import me.fjm.entity.Account;
import me.fjm.repository.AccountRepository;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
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
        Account retrievedAccount = resources.target("/account/1").request().get(Account.class);

        assertNotNull(retrievedAccount);
        assertEquals(account.getId(), retrievedAccount.getId());
        assertEquals(account.getBalance(), retrievedAccount.getBalance());
        assertEquals(account.getName(), retrievedAccount.getName());

    }

    @Test
    public void testGetInvalidAccount() {
        try {
            resources.target("/account/100").request().get(Account.class);
        } catch (NotFoundException e) {
            assertEquals("HTTP 404 Not Found", e.getMessage());
        }

    }



    @Test
    public void testGetAllAccounts() {
        List<Account> retrievedAccounts = resources.target("/account").request().get(List.class);

        assertNotNull(retrievedAccounts);
        assertEquals(3, retrievedAccounts.size());

    }


    @Test
    public void testCreateAccount() {

        Account newAccount = new Account();
        newAccount
                .setName("New User")
                .setBalance(30.0);

        Account savedAccount = resources.target("/account").request().post(Entity.json(newAccount), Account.class);

        assertNotNull(savedAccount);
        assertEquals(newAccount.getName(), savedAccount.getName());
        assertEquals(newAccount.getBalance(), savedAccount.getBalance());

        repository.delete(savedAccount.getId());
    }

    @Test
    public void testUpdateAccount() {

        Account newAccount = new Account();
        newAccount.setName("Updated Name");

        Account updatedAccount = resources.target("/account/1").request().put(Entity.json(newAccount), Account.class);

        assertNotNull(updatedAccount);
        assertEquals(newAccount.getName(), updatedAccount.getName());
        assertEquals(account.getBalance(), updatedAccount.getBalance());

    }

    @Test
    public void testUpdateInexistentAccount() {

        Account newAccount = new Account();
        newAccount.setName("Updated Name");

        try {
            resources.target("/account/999").request().put(Entity.json(newAccount), Account.class);
        } catch (NotFoundException e) {
            assertEquals("HTTP 404 Not Found", e.getMessage());
        }

    }

    @Test
    public void testDeleteAccount() {

        Account newAccount = new Account();
        newAccount
                .setName("New User")
                .setBalance(30.0);

        repository.save(newAccount);

        resources.target("/account/4").request().delete(Account.class);
        try {
            resources.target("/account/100").request().get(Account.class);
        } catch (NotFoundException e) {
            assertEquals("HTTP 404 Not Found", e.getMessage());
        }


    }

}
