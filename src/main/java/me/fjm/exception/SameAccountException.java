package me.fjm.exception;

public class SameAccountException extends Exception {

    public SameAccountException() {
        super("It is not possible to do transference to the same account as the sender.");
    }
}
