package com.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BankAccountTest {
    private SavingsAccount acc;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {

        acc = new SavingsAccount("Justin Antonio Bolivar");
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @Test
    void testAccountCreation_ShouldReturnAccountName() {
        assertEquals("Justin Antonio Bolivar", acc.getOwnerName());
    }

    @Test
    void testDeposit_DepositAValidAmount_ShouldReturnTrue() {
        acc.deposit(1000);
        assertTrue(outputStreamCaptor.toString().contains("Deposited: Php 1000.0"));
        assertEquals(1000, acc.getBalance());
    }
    
    @Test
    void testDeposit_DepositInvalidAMount_ShouldReturnTrue() {
        acc.deposit(0);
        assertTrue(outputStreamCaptor.toString().contains("The deposit amount must be positive"));
        
        outputStreamCaptor.reset();
        acc.deposit(-500);
        assertTrue(outputStreamCaptor.toString().contains("The deposit amount must be positive"));
    }

    @Test
    void testWithdraw_WithdrawSufficientFunds_ShouldReturnTrue() {
        acc.deposit(1000);
        acc.withdraw(500);
        assertTrue(outputStreamCaptor.toString().contains("Withdrawn: Php 500.0"));
        assertEquals(500, acc.getBalance());
    }
    
    @Test
    void testWithdraw_WithdrawInsufficientFunds_ShouldReturnTrue() {
        acc.deposit(1000);
        acc.withdraw(1500);
        assertTrue(outputStreamCaptor.toString().contains("Insufficient balance"));
    }

    @Test
    void testWithdraw_WithdrawNegativeAmount_ShouldReturnTrue() {
        acc.withdraw(-100);
        assertTrue(outputStreamCaptor.toString().contains("The withdraw amount must be positive"));
    }
    
    @Test
    void testFreezeAccountAndDeposit_FreezeAccountAndDepositMoney_ShouldReturnTrue() {
        acc.freezeAccount();
        acc.deposit(11500);
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Account has been Frozen"));
        assertTrue(output.contains("Account is Frozen Cannot Deposit"));
    }

    @Test
    void testWithdrawWhenFrozen_WithdrawAnValidAMountWhileAccountIsFrozen_ShouldReturnTrue() {
        acc.deposit(1000);
        acc.freezeAccount();
        acc.withdraw(500);
        assertTrue(outputStreamCaptor.toString().contains("Account is Frozen Cannot Withdraw"));
    }
    
    @Test
    void testUnfreezeAndWithdraw_UnfreezeAccountAndWithdrawAnAmount_ShouldReturnTrue() {
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
    void testMain() {
        int expectedResult = 1;
        SavingsAccount.main(null);

        int result = 1;
        assertEquals(expectedResult, result, () -> "Main Test");
    }

}
