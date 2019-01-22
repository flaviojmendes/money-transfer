package me.fjm.service;

import me.fjm.api.Account;
import me.fjm.api.Receipt;
import me.fjm.exception.InsufficientFundsException;
import me.fjm.exception.InvalidAccountException;
import me.fjm.db.AccountRepository;
import me.fjm.db.ReceiptRepository;
import me.fjm.exception.SameAccountException;

public class TransferService {

    private AccountRepository accountRepository;
    private ReceiptRepository receiptRepository;

    public TransferService(AccountRepository accountRepository, ReceiptRepository receiptRepository) {
        this.accountRepository = accountRepository;
        this.receiptRepository = receiptRepository;
    }

    public Receipt transferBetweenAccounts(Long fromId, Long toId, Double amount) throws InvalidAccountException, InsufficientFundsException, SameAccountException {
        if(fromId == toId) {
            throw new SameAccountException();
        }

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

        return receiptRepository.save(receipt);

    }

}
