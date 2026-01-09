package com.bank;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BankAccountManager {
    /**
     * accounts field to map into the hash map.
     */
    private Map<Integer, BankAccount> accounts;
    /**
     * nextAccountId field to make an accountID for the next account.
     */
    private int nextAccountId;
    /**
     * BankAccountManager constructor to set the values of
     * accounts and nextAccountId.
     */
    public BankAccountManager() {
        accounts = new HashMap<>();
        nextAccountId = 1;
    }
    /**
     * addAccount function to add an account to the accounts hash map.
     * @param account
     */
    void addAccount(final BankAccount account) {
        accounts.put(nextAccountId, account);
        System.out.println("Account ID: " + nextAccountId
                + ", Balance: " + account.getBalance());
        nextAccountId++;
    }
    /**
     * getAccount function to get an account with the specific account ID.
     * @param accountId
     * @return account information
     */
    BankAccount getAccount(final int accountId) {
        BankAccount account = accounts.get(accountId);
        return Objects.requireNonNull(account,
                "Account not found for ID: " + accountId);
    }
    /**
     * listAccounts function to list the accounts in the hash map.
     */
    void listAccounts() {
        accounts.forEach((id, account) -> {
            System.out.println("ID: " + id);
        });
    }
    /**
     * filterTranasctionsAbove to filter the transactions
     * and only return the transactions with >= to the amount..
     * @param amount
     * @param txList
     * @return FilteredList of transactions
     */
    List<Transaction> filterTransactionsAbove(final double amount,
            final List<Transaction> txList) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "The deposit amount must be a valid amount "
                    + "greater than zero. Received: "
                            + amount);
        } else {
            return txList.stream()
                    .filter(tx -> tx.getAmount() >= amount)
                    .collect(Collectors.toList());
        }
    }
    /**
     * sortTransactionsByAmount function to sort transaction list.
     * @param txList
     * @return sorted list of transactions
     */
    List<Transaction> sortTransactionsByAmount(final List<Transaction> txList) {
        return txList.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .collect(Collectors.toList());
    }
}
