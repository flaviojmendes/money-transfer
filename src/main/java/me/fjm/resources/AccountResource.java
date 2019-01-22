package me.fjm.resources;

import io.dropwizard.jersey.params.LongParam;
import me.fjm.api.Account;
import me.fjm.db.AccountRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private AccountRepository accountRepository;

    public AccountResource(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GET
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @GET
    @Path("{id}")
    public Account getAccount(@PathParam("id") LongParam id) {
        return accountRepository.findById(id.get())
                .orElseThrow(() ->
                        new WebApplicationException("Account not found", 404));
    }


    @POST
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @PUT
    @Path("{id}")
    public Account update(@PathParam("id") LongParam id, Account account) {
        return accountRepository.update(id.get(), account)
                .orElseThrow(() ->
                        new WebApplicationException("Account not found", 404));
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        accountRepository.delete(id.get());
        return Response.ok().build();
    }


}
