package me.fjm.resources;

import io.dropwizard.jersey.params.LongParam;
import me.fjm.api.Receipt;
import me.fjm.db.ReceiptRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/receipt")
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptResource {

    private ReceiptRepository receiptRepository;

    public ReceiptResource(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @GET
    @Path("/list")
    public List<Receipt> getReceipts() {
        return receiptRepository.findAll();
    }

    @GET
    public Receipt getReceipt(@QueryParam("id") LongParam id) {
        return receiptRepository.findById(id.get())
                .orElseThrow(() ->
                        new WebApplicationException("Receipt not found", 404));
    }

}
