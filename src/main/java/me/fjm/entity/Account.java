package me.fjm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Account extends Entity{

    public Account() {
        super();
    }

    public Account(Long id, String name, Double balance) {
        super(id);
        this.name = name;
        this.balance = balance;
    }

    @Length(min=2, max=250)
    @NotEmpty
    private String name;

    @NotEmpty
    private Double balance;


    @JsonProperty
    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public Account setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    @JsonProperty
    public Double getBalance() {
        return balance;
    }

}
