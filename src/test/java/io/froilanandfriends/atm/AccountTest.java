package io.froilanandfriends.atm;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by johnb on 2/8/16.
 */
public class AccountTest {



    @Test
    public void testStringGetId() throws Exception {
        //account object made from single string has intended ID
        String inputString = "CHECKING, 123456";
        Account account = new Account(inputString);
        assertEquals(123456, account.getId());
}

    @Test
    public void testWithdraw() throws Exception {
        Account a = new Account(AccountType.SAVINGS);
        assertEquals("Account balance should be 0",0,a.getBalance(),0.01d);
        a.deposit(100.00d);
        assertEquals("Account balance should now be 100.00",100.00d,a.getBalance(),0.01d);
        a.withdraw(23.55d);
        assertEquals("Account balance should now be 76.45",76.45d,a.getBalance(),0.01d);
    }

    @Test
    public void testDeposit() throws Exception {
        Account a = new Account(AccountType.CHECKING);
        assertEquals("Account balance should be 0",0,a.getBalance(),0.01d);
        a.deposit(123.03d);
        assertEquals("account balance should now be 123.03",123.03d,a.getBalance(),0.01d);
        a.deposit(10.00d);
        assertEquals("account balance should now be 133.03",133.03d,a.getBalance(),0.01d);
    }

    @Test
    public void testGetAccountType() throws Exception {
        Account a1 = new Account(AccountType.SAVINGS);
        assertEquals("Account1 type should be SAVINGS",AccountType.SAVINGS,a1.getAccountType());
        Account a2 = new Account(AccountType.CHECKING);
        assertEquals("Account2 type should be CHECKING",AccountType.CHECKING,a2.getAccountType());
        Account a3 = new Account(AccountType.BUSINESS);
        assertEquals("Account3 type should be BUSINESS",AccountType.BUSINESS,a3.getAccountType());
    }

    @Test
    public void testGetId() throws Exception {
        Account a1 = new Account(AccountType.BUSINESS);
        assertNotEquals("a1's id sould not be 0",0,a1.getId());
    }


    @Test
    public void testGetUserIDs() {
        Account a = new Account(AccountType.BUSINESS);
        a.getUserIDs().add(12312);
        a.getUserIDs().add(3423423);
        assertEquals("account's userids should be of size 2",2,a.getUserIDs().size());
    }
    @Test
    public void testGetBalance() throws Exception {
        Account a = new Account(AccountType.BUSINESS);
        assertEquals("Account balance should be 0",0,a.getBalance(),0.01d);
        a.deposit(59.30d);
        assertEquals("Account balance should now be 100.00",59.30d,a.getBalance(),0.01d);
    }
}