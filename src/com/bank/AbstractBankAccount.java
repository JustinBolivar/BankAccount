package com.bank;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBankAccount implements BankAccount {
    /**
     * balance field that holds the the account balance amount.
     */
    private double balance;
    /**
     * isFrozen field to hold the status of the account.
     */
    private boolean isFrozen;
    /**
     * transactionHistort field to hold the transaction history of the account.
     */
    private List<Transaction> transactionHistory;

    /**
     * Abstract Bank Account Constructor which initializes the balance to 0 and
     * set isFrozen to false.
     */
    public AbstractBankAccount() {
        balance = 0;
        isFrozen = false;
        transactionHistory = new ArrayList<>();
    }

    /**
     * Deposit method to deposit an amount to a bank account.
     * @param amount
     */
    public void deposit(final double amount)
            throws InvalidAmountException, AccountFrozenException {
        if (isAccountFrozen()) {
            throw new AccountFrozenException(
                    "Account is Frozen. Cannot Deposit.");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "The deposit amount must be a valid amount "
                    + "greater than zero. Received: "
                            + amount);
        }

        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("Deposited: Php " + amount);
    }

    /**
     * Withdraw method to withdraw a certain amount to a bank account.
     * @param amount
     */
    public void withdraw(final double amount) throws InvalidAmountException,
            AccountFrozenException, InsufficientFundsException {
        if (isAccountFrozen()) {
            throw new AccountFrozenException(
                    "Account is Frozen. Cannot withdraw.");
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "The withdrawal amount must be a valid amount "
                    + "greater than zero. Received: "
                            + amount);
        }

        if (balance < amount) {
            throw new InsufficientFundsException(
                    "Insufficient balance. Current balance: Php " + balance);
        }

        balance -= amount;
        transactionHistory.add(new Transaction("Withdraw", amount));
        System.out.println("Withdrawn: Php " + amount);
    }

    /**
     * getBalance method to fetch the total balance of a account.
     * @return balance
     */
    public double getBalance() {
        System.out.println("Balance: " + balance);
        return balance;
    }

    /**
     * isAccountFrozen method to check the status of a account.
     * @return isFrozen
     */
    public boolean isAccountFrozen() {
        return isFrozen;
    }

    /**
     * freezeAcount method to freeze an account.
     */
    public void freezeAccount() {
        isFrozen = true;
        System.out.println("Account has been Frozen");
    }

    /**
     * unFreezeAccount method to unFreeze an account.
     */
    public void unFreezeAccount() {
        isFrozen = false;
        System.out.println("Account has been unfrozen");
    }

    /**
     * getTransactionHistory function to get the transaction history of the
     * account.
     * @return transactionHistory
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

}
