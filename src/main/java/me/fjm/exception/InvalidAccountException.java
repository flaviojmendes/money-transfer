package me.fjm.exception;

public class InvalidAccountException extends Exception {

    public InvalidAccountException(Long id) {
        super("The account " + id + " does not exist.");
    }
}
