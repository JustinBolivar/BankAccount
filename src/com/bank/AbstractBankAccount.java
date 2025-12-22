package com.bank;

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
     * Abstract Bank Account Constructor which initializes the balance to 0 and
     * set isFrozen to false.
     */
    public AbstractBankAccount() {
        balance = 0;
        isFrozen = false;
    }
    /**
     * Deposit method to deposit an amount to a bank account.
     * @param amount
     */
    public void deposit(final double amount) {
        if (isAccountFrozen()) {
            System.out.println("Account is Frozen Cannot Deposit");
        } else {
            if (amount == 0 || amount < 0) {
                System.out.println("The deposit amount must be positive");
            } else {
                balance += amount;
                System.out.println("Deposited: Php " + amount);
            }
        }
    }
    /**
     * Withdraw method to withdraw a certain amount to a bank account.
     * @param amount
     */
    public void withdraw(final double amount) {
        if (isAccountFrozen()) {
            System.out.println("Account is Frozen Cannot Withdraw");
        } else {
            if (amount == 0 || amount < 0) {
                System.out.println("The withdraw amount must be positive");
            } else {
                if (balance > amount) {
                    balance -= amount;
                    System.out.println("Withdrawn: Php " + amount);
                } else {
                    System.out.println("Insufficient balance");
                }

            }
        }
    }
    /**
     * getBalance method to fetch the total balance of a account.
     * @return balance
     */
    public double getBalance() {
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
    void freezeAccount() {
        isFrozen = true;
        System.out.println("Account has been Frozen");
    }
    /**
     * unFreezeAccount method to unFreeze an account.
     */
    void unFreezeAccount() {
        isFrozen = false;
        System.out.println("Account has been unfrozen");
    }

}
