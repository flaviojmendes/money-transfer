package me.fjm.exception;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(Long id) {
        super("The account " + id + " has insufficient funds.");
    }
}
