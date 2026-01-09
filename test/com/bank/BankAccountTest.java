package com.bank;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountTest {
    private SavingsAccount acc;
    private BankAccountManager manager;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        manager = new BankAccountManager();
        acc = new SavingsAccount("Justin Antonio Bolivar");
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testAccountCreation_ShouldReturnAccountName() {
        assertEquals("Justin Antonio Bolivar", acc.getOwnerName());
    }

    @Test
    void testDeposit_ValidAmount()
            throws InvalidAmountException, AccountFrozenException {
        acc.deposit(1000);
        assertTrue(outputStreamCaptor.toString()
                .contains("Deposited: Php 1000.0"));
        assertEquals(1000, acc.getBalance());
    }

    @Test
    void testDeposit_InvalidAmount_ThrowsException() {
        InvalidAmountException ex = assertThrows(InvalidAmountException.class,
                () -> acc.deposit(0));
        assertTrue(ex.getMessage()
                .contains("The deposit amount must be a valid amount"));
    }

    @Test
    void testWithdraw_SufficientFunds() throws Exception {
        acc.deposit(1000);
        acc.withdraw(500);
        assertTrue(
                outputStreamCaptor.toString().contains("Withdrawn: Php 500.0"));
        assertEquals(500, acc.getBalance());
    }

    @Test
    void testWithdraw_InsufficientFunds_ThrowsException() throws Exception {
        acc.deposit(1000);
        assertThrows(InsufficientFundsException.class,
                () -> acc.withdraw(1500));
    }

    @Test
    void testWithdraw_NegativeAmount_ThrowsException() {
        InvalidAmountException ex = assertThrows(InvalidAmountException.class,
                () -> acc.withdraw(-100));
        assertTrue(ex.getMessage()
                .contains("The withdrawal amount must be a valid amount"));
    }

    @Test
    void testFreezeAccount_DepositFails() {
        acc.freezeAccount();
        AccountFrozenException ex = assertThrows(AccountFrozenException.class,
                () -> acc.deposit(11500));
        assertTrue(
                ex.getMessage().contains("Account is Frozen. Cannot Deposit"));
    }

    @Test
    void testWithdraw_WhenFrozen_ThrowsException() throws Exception {
        acc.deposit(1000);
        acc.freezeAccount();

        AccountFrozenException ex = assertThrows(AccountFrozenException.class,
                () -> acc.withdraw(500));
        assertTrue(
                ex.getMessage().contains("Account is Frozen. Cannot withdraw"));
    }

    @Test
    void testUnfreezeAndWithdraw_Success() throws Exception {
        acc.deposit(1000);
        acc.freezeAccount();
        acc.unFreezeAccount();
        acc.withdraw(100);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Account has been unfrozen"));
        assertTrue(output.contains("Withdrawn: Php 100.0"));
        assertEquals(900, acc.getBalance());
    }

    @Test
    void testListAccounts_ShouldReturnTrue() {
        manager.addAccount(acc);
        outputStreamCaptor.reset();
        manager.listAccounts();
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("ID: 1"),
                "Output should contain Account ID 1");
    }

    @Test
    void testTransactionGetType_ShouldReturnTrue() {
        Transaction tx = new Transaction("Deposit", 500.0);
        assertEquals("Deposit", tx.getType(),
                "Transaction type should match the input");
    }

    @Test
    void testGetAccount_ValidId_ShouldReturnAccount() {
        manager.addAccount(acc);
        BankAccount retrievedAccount = manager.getAccount(1);
        assertNotNull(retrievedAccount);
    }

    @Test
    void testGetAccount_InvalidId_ShouldThrowException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> {
                    manager.getAccount(999);
                });
        assertTrue(ex.getMessage().contains("Account not found for ID: 999"));
    }

    @Test
    void testFilterTransactionsAbove_ValidAmount_ShouldReturnFilteredList()
            throws InvalidAmountException {
        // Setup dummy transactions
        java.util.List<Transaction> history = java.util.Arrays.asList(
                new Transaction("Deposit", 100.0),
                new Transaction("Deposit", 500.0),
                new Transaction("Withdraw", 50.0));

        // Filter for transactions >= 100
        java.util.List<Transaction> result = manager
                .filterTransactionsAbove(100.0, history);

        // Assertions
        assertEquals(2, result.size(), "Should find 2 transactions >= 100");
        assertTrue(result.stream().allMatch(t -> t.getAmount() >= 100.0));
    }

    @Test
    void testFilterTransactionsAbove_InvalidAmount_ShouldThrowException() {
        // Setup dummy transactions
        java.util.List<Transaction> history = java.util.Arrays
                .asList(new Transaction("Deposit", 100.0));

        // Assert that passing -50.0 throws InvalidAmountException
        InvalidAmountException ex = assertThrows(InvalidAmountException.class,
                () -> {
                    manager.filterTransactionsAbove(-50.0, history);
                });

        assertTrue(ex.getMessage().contains("greater than zero"),
                "Error message should mention amount rules");
    }

    @Test
    void testGetTransactionHistory_ShouldReturnCorrectHistory()
            throws Exception {
        assertTrue(acc.getTransactionHistory().isEmpty(),
                "Initial history should be empty");
        acc.deposit(1000.0);
        acc.withdraw(400.0);
        java.util.List<Transaction> history = acc.getTransactionHistory();
        assertEquals(2, history.size(),
                "History should contain exactly 2 transactions");
        assertEquals("Deposit", history.get(0).getType());
        assertEquals(1000.0, history.get(0).getAmount());
        assertEquals("Withdraw", history.get(1).getType());
        assertEquals(400.0, history.get(1).getAmount());
    }

    @Test
    void testTransactionToString_ShouldReturnFormattedString() {
        Transaction tx = new Transaction("Deposit", 1500.0);
        String expected = "Type: Deposit, Amount: 1500.0";
        assertEquals(expected, tx.toString(),
                "The toString format should match 'Type: [type], Amount: [amount]'");
    }

    @Test
    void testSortTransactionsByAmount_ShouldReturnSortedList() {
        java.util.List<Transaction> unsorted = java.util.Arrays.asList(
                new Transaction("Deposit", 500.0),
                new Transaction("Withdraw", 50.0),
                new Transaction("Deposit", 1000.0),
                new Transaction("Withdraw", 200.0));
        java.util.List<Transaction> sorted = manager
                .sortTransactionsByAmount(unsorted);
        assertEquals(4, sorted.size(), "List size should remain the same");
        assertEquals(50.0, sorted.get(0).getAmount());
        assertEquals(200.0, sorted.get(1).getAmount());
        assertEquals(500.0, sorted.get(2).getAmount());
        assertEquals(1000.0, sorted.get(3).getAmount());
    }

    @Test
    void testMain() {
        assertDoesNotThrow(() -> SavingsAccount.main(null));
    }
}
