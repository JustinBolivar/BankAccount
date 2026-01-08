package com.bank;

public class AccountFrozenException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public AccountFrozenException(String message) {
        super(message);
    }
}
