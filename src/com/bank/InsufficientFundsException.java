package com.bank;

public class InsufficientFundsException extends RuntimeException {
    /**
     * serialID to suppress error.
     */
    private static final long serialVersionUID = 1L;

    /**
     * InsufficientFundsException used to be thrown when the amount
     * in the account is insufficient.
     * @param message
     */
    public InsufficientFundsException(final String message) {
        super(message);
    }
}
