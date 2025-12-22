package com.bank;

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
    /**
     * Main Function.
     * @param args
     */
    public static void main(final String[] args) {
        SavingsAccount acc = new SavingsAccount("Justin Antonio Bolivar");
        System.out.println(acc.getOwnerName());
        final int a = 1000;
        final int b = 0;
        final int c = -500;
        final int d = 500;
        final int e = -100;
        final int f = 11500;
        final int g = 100;
        acc.deposit(a);
        acc.deposit(b);
        acc.deposit(c);
        acc.withdraw(d);
        acc.withdraw(a + d);
        acc.withdraw(e);
        acc.freezeAccount();
        acc.deposit(f);
        acc.withdraw(d);
        acc.unFreezeAccount();
        acc.withdraw(g);
        System.out.println(acc.isAccountFrozen());
        System.out.println(acc.getBalance());
    }
}
