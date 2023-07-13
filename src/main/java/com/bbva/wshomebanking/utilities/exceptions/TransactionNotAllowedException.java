package com.bbva.wshomebanking.utilities.exceptions;

public class TransactionNotAllowedException extends Exception{

    public TransactionNotAllowedException(String message) {
        super(message);
    }
}
