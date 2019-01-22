package me.fjm.resources;


import me.fjm.api.Receipt;
import me.fjm.exception.InsufficientFundsException;
import me.fjm.exception.InvalidAccountException;
import me.fjm.db.AccountRepository;
import me.fjm.db.ReceiptRepository;
import me.fjm.exception.SameAccountException;
import me.fjm.service.TransferService;
import org.eclipse.jetty.http.HttpStatus;

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

        try {
            Receipt receipt = transferService.transferBetweenAccounts(idFrom, idTo, amount);
            return receipt;
        } catch (InvalidAccountException e) {
            throw new WebApplicationException(e.getMessage(), HttpStatus.NOT_FOUND_404);
        } catch (InsufficientFundsException | SameAccountException e) {
            throw new WebApplicationException(e.getMessage(), HttpStatus.FORBIDDEN_403);
        }
    }

}
