package com.bank;

public class InvalidAmountException extends RuntimeException {
    /**
     * serialID to suppress error.
     */
    private static final long serialVersionUID = 1L;
    /**
     * InvalidAmountException used to be thrown
     * when an amount is an invalid number.
     * @param message
     */
    public InvalidAmountException(final String message) {
        super(message);
    }
}
