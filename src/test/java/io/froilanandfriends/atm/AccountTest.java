package io.froilanandfriends.atm;

import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * Created by johnb on 2/8/16.
 */
public class AccountTest {
    @BeforeClass
    public static void setupUser() {
        UserManager.getUserManager().setCurrentUser(null);

    }


    @Test
    public void testConstructorWithStringArg(){


    }


    @Test
    public void testStringGetId() throws Exception {
        //account object made from single string has intended ID
        String inputString = "CHECKING, 123456, 2.24, 42";
        Account account = new BusinessAccount(inputString);
        assertEquals(123456, account.getId());
}

    @Test
    public void testWithdraw() throws Exception {
        Account a = new SavingsAccount();
        assertEquals("Account balance should be 0",0,a.getBalance(),0.01d);
        a.deposit(100.00d);
        assertEquals("Account balance should now be 100.00",100.00d,a.getBalance(),0.01d);
        a.withdraw(23.55d);
        assertEquals("Account balance should now be 76.45",76.45d,a.getBalance(),0.01d);
    }

    @Test
    public void testDeposit() throws Exception {
        Account a = new CheckingAccount();
        assertEquals("Account balance should be 0",0,a.getBalance(),0.01d);
        a.deposit(123.03d);
        assertEquals("account balance should now be 123.03",123.03d,a.getBalance(),0.01d);
        a.deposit(10.00d);
        assertEquals("account balance should now be 133.03",133.03d,a.getBalance(),0.01d);
    }

    @Test
    public void testGetAccountType() throws Exception {
        Account a1 = new SavingsAccount();
        assertEquals("Account1 type should be SAVINGS",Account.AccountType.SAVINGS,a1.getAccountType());
        Account a2 = new CheckingAccount();
        assertEquals("Account2 type should be CHECKING",Account.AccountType.CHECKING,a2.getAccountType());
        Account a3 = new BusinessAccount();
        assertEquals("Account3 type should be BUSINESS",Account.AccountType.BUSINESS,a3.getAccountType());
    }

    @Test
    public void testGetId() throws Exception {
        Account a1 = new BusinessAccount();
        assertNotEquals("a1's id should not be 0",0,a1.getId());
    }


    @Test
    public void testGetUserIDs() {
        Account a = new BusinessAccount();
        a.getUserIDs().add(12312);
        a.getUserIDs().add(3423423);
        assertEquals("account's userids should be of size 2",2,a.getUserIDs().size());
    }
    @Test
    public void testGetBalance() throws Exception {
        Account a = new BusinessAccount();
        assertEquals("Account balance should be 0",0,a.getBalance(),0.01d);
        a.deposit(59.30d);
        assertEquals("Account balance should now be 100.00",59.30d,a.getBalance(),0.01d);
    }
}