package me.fjm.db;

import me.fjm.api.Account;

public class AccountRepository extends Repository<Account> {
    private static final String DATA_SOURCE = "dummy_accounts.json";

    public AccountRepository() {
        super(DATA_SOURCE, Account.class);
    }


}
