package com.bank;

public class Transaction {
    /**
     * type field to identify the type of transaction.
     */
    private String type;
    /**
     * amount field to identify the amount that was withdrawn or deposited in
     * the transaction.
     */
    private double amount;
    /**
     * Transaction constructor that sets the type and amount of the transaction.
     * @param types
     * @param amounts
     */
    public Transaction(final String types, final double amounts) {
        super();
        this.type = types;
        this.amount = amounts;
    }
    /**
     * getType function to get the type of transaction.
     * @return type
     */
    public String getType() {
        return type;
    }
    /**
     * getAmount function to return the amount of the transaction.
     * @return amount
     */
    public double getAmount() {
        return amount;
    }
    /**
     * toString function to print the transaction in string type.
     * @return Type and Amount String
     */
    public String toString() {
        return "Type: " + this.type + ", Amount: " + this.amount;
    }
}
