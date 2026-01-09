package com.bank;

public class AccountFrozenException extends RuntimeException {
    /**
     * serialID to suppress error.
     */
    private static final long serialVersionUID = 1L;

    /**
     * AccountFrozenException used to be thrown when the amount an action is to
     * be done while the account is frozen.
     * @param message
     */
    public AccountFrozenException(final String message) {
        super(message);
    }
}
