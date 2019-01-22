package me.fjm.entity;

public class Receipt extends Entity{

    public Receipt() {
        super();
    }


    private Double amount;
    private Account from;
    private Account to;

    public Double getAmount() {
        return amount;
    }

    public Receipt setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Account getFrom() {
        return from;
    }

    public Receipt setFrom(Account from) {
        this.from = from;
        return this;
    }

    public Account getTo() {
        return to;
    }

    public Receipt setTo(Account to) {
        this.to = to;
        return this;
    }
}
