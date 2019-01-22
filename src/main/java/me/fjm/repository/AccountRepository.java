package me.fjm.repository;

import me.fjm.entity.Account;

public class AccountRepository extends Repository<Account> {
    private static final String DATA_SOURCE = "dummy_accounts.json";

    public AccountRepository() {
        super(DATA_SOURCE);
    }


}
