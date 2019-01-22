package me.fjm.resources;


import me.fjm.api.Receipt;
import me.fjm.exception.InsufficientFundsException;
import me.fjm.exception.InvalidAccountException;
import me.fjm.db.AccountRepository;
import me.fjm.db.ReceiptRepository;
import me.fjm.service.TransferService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    private TransferService transferService;

    public TransferResource(AccountRepository accountRepository, ReceiptRepository receiptRepository) {
        this.transferService = new TransferService(accountRepository, receiptRepository);
    }


    @POST
    public Receipt transfer(@QueryParam("from") Long idFrom, @QueryParam("to") Long idTo, @QueryParam("amount") Double amount) {
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
