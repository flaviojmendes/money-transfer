package me.fjm.resources;


import io.dropwizard.jersey.params.LongParam;
import me.fjm.entity.Receipt;
import me.fjm.exception.InsufficientFundsException;
import me.fjm.exception.InvalidAccountException;
import me.fjm.repository.AccountRepository;
import me.fjm.service.TransferService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    private AccountRepository accountRepository;
    private TransferService transferService;

    public TransferResource(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.transferService = new TransferService(accountRepository);
    }


    @POST
    public Receipt getAccount(@QueryParam("from") Long idFrom, @QueryParam("to") Long idTo, @QueryParam("amount") Double amount) {
        Receipt receipt = null;
        try {
            receipt = transferService.transferBetweenAccounts(idFrom, idTo, amount);
        } catch (InvalidAccountException e) {
            throw new WebApplicationException(e.getMessage(), 404);
        } catch (InsufficientFundsException e) {
            throw new WebApplicationException(e.getMessage(), 403);
        }

        return receipt;
    }


}
