package com.bank;

import java.util.List;

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

    /**
     * freezeAccount method to freeze account.
     */
    void freezeAccount();

    /**
     * unFreezeAccount method to unFreeze account.
     */
    void unFreezeAccount();

    /**
     * getTransactionHistory function to get the transaction history of the
     * account.
     * @return transactionHistory
     */
    List<Transaction> getTransactionHistory();
}
