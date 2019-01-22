package me.fjm.service;

import me.fjm.entity.Account;
import me.fjm.entity.Receipt;
import me.fjm.exception.InsufficientFundsException;
import me.fjm.exception.InvalidAccountException;
import me.fjm.repository.AccountRepository;

import java.util.Optional;

public class TransferService {

    private AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Receipt transferBetweenAccounts(Long fromId, Long toId, Double amount) throws InvalidAccountException, InsufficientFundsException {
        Account accountFrom = accountRepository.findById(fromId).orElseThrow(() -> new InvalidAccountException(fromId));
        Account accountTo = accountRepository.findById(toId).orElseThrow(() -> new InvalidAccountException(toId));

        if(accountFrom.getBalance() < amount) {
            throw new InsufficientFundsException(accountFrom.getId());
        }

        accountFrom.setBalance(accountFrom.getBalance() - amount);
        accountTo.setBalance(accountTo.getBalance() + amount);

        Receipt receipt = new Receipt();
        receipt.setAmount(amount)
                .setFrom(accountFrom)
                .setTo(accountTo);

        return receipt;

    }

}
