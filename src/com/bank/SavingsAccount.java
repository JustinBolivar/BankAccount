package com.bank;

import java.util.List;

public class SavingsAccount extends AbstractBankAccount {
    /**
     * ownerName field to store the name of the account owner.
     */
    private String ownerName;

    /**
     * Savings Account constructor to set the owner.
     * @param name
     */
    public SavingsAccount(final String name) {
        super();
        this.ownerName = name;
    }

    /**
     * getOwnerName method to get the account owner name.
     * @return ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    private static void performBankAction(final Runnable action) {
        try {
            action.run();
        } catch (InvalidAmountException | AccountFrozenException
                | InsufficientFundsException | NullPointerException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------");
    }

    /**
     * Main function to test and run program.
     * @param args
     */
    public static void main(final String[] args) {
        BankAccountManager manager = new BankAccountManager();
        final int num1 = 1000;
        final int num2 = 500;
        final int num3 = 1500;
        final int num4 = 100;
        SavingsAccount acc = new SavingsAccount("Justin Antonio Bolivar");
        manager.addAccount(acc);
        performBankAction(() -> acc.deposit(num1));
        performBankAction(() -> acc.deposit(0));
        performBankAction(() -> acc.withdraw(num2));
        performBankAction(() -> acc.deposit(-num2));
        performBankAction(() -> acc.withdraw(num3));
        performBankAction(() -> acc.withdraw(-num4));
        performBankAction(() -> acc.freezeAccount());
        performBankAction(() -> acc.deposit(num2));
        performBankAction(() -> acc.withdraw(num2));
        performBankAction(() -> acc.unFreezeAccount());
        performBankAction(() -> acc.withdraw(num4));
        performBankAction(() -> acc.getBalance());
        
        try {
            List<Transaction> filtered = manager.filterTransactionsAbove(-num2, acc.getTransactionHistory());
            filtered.forEach(f -> System.out.println(f));
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------");
        List<Transaction> sort = manager
                .sortTransactionsByAmount(acc.getTransactionHistory());
        sort.forEach(s -> System.out.println(s));
        System.out.println("----------------------------------------");
        performBankAction(() -> manager.getAccount(2));
    }
}
