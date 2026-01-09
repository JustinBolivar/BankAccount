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
    void testDeposit_ValidAmount() throws InvalidAmountException, AccountFrozenException {
        acc.deposit(1000);
        assertTrue(outputStreamCaptor.toString().contains("Deposited: Php 1000.0"));
        assertEquals(1000, acc.getBalance());
    }

    @Test
    void testDeposit_InvalidAmount_ThrowsException() {
        InvalidAmountException ex = assertThrows(InvalidAmountException.class, () -> acc.deposit(0));
        assertTrue(ex.getMessage().contains("The deposit amount must be a valid amount"));
    }

    @Test
    void testWithdraw_SufficientFunds() throws Exception {
        acc.deposit(1000);
        acc.withdraw(500);
        assertTrue(outputStreamCaptor.toString().contains("Withdrawn: Php 500.0"));
        assertEquals(500, acc.getBalance());
    }

    @Test
    void testWithdraw_InsufficientFunds_ThrowsException() throws Exception {
        acc.deposit(1000);
        assertThrows(InsufficientFundsException.class, () -> acc.withdraw(1500));
    }

    @Test
    void testWithdraw_NegativeAmount_ThrowsException() {
        InvalidAmountException ex = assertThrows(InvalidAmountException.class, () -> acc.withdraw(-100));
        assertTrue(ex.getMessage().contains("The withdrawal amount must be a valid amount"));
    }

    @Test
    void testFreezeAccount_DepositFails() {
        acc.freezeAccount();
        AccountFrozenException ex = assertThrows(AccountFrozenException.class, () -> acc.deposit(11500));
        assertTrue(ex.getMessage().contains("Account is Frozen. Cannot Deposit"));
    }

    @Test
    void testWithdraw_WhenFrozen_ThrowsException() throws Exception {
        acc.deposit(1000);
        acc.freezeAccount();
        
        AccountFrozenException ex = assertThrows(AccountFrozenException.class, () -> acc.withdraw(500));
        assertTrue(ex.getMessage().contains("Account is Frozen. Cannot withdraw"));
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
        assertTrue(output.contains("ID: 1"), "Output should contain Account ID 1");
    }
    
    @Test
    void testTransactionGetType_ShouldReturnTrue() {
        Transaction tx = new Transaction("Deposit", 500.0);
        assertEquals("Deposit", tx.getType(), "Transaction type should match the input");
    }
    
    @Test
    void testGetAccount_ValidId_ShouldReturnAccount() {
        manager.addAccount(acc);
        BankAccount retrievedAccount = manager.getAccount(1);
        assertNotNull(retrievedAccount);
    }
    
    @Test
    void testGetAccount_InvalidId_ShouldThrowException() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> {
            manager.getAccount(999);
        });
        assertTrue(ex.getMessage().contains("Account not found for ID: 999"));
    }

    @Test
    void testMain() {
        assertDoesNotThrow(() -> SavingsAccount.main(null));
    }
}
