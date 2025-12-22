package com.bank;

public interface BankAccount {
    /**
     * deposit method to deposit an amount to a account.
     * @param amount
     */
    void deposit(double amount);

    /**
     * withdraw method to withdraw an amount to a account.
     * @param amount
     */
    void withdraw(double amount);

    /**
     * getBalance method to get the account balance.
     * @return account balance.
     */
    double getBalance();

    /**
     * is AccountFrozen method to check account status.
     * @return true or false depending on account status.
     */
    boolean isAccountFrozen();
}
