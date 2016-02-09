package io.froilanandfriends.atm;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountManagerTest {

    @Test
    public void testGetAccountManager() throws Exception {
        assertNotNull("getaccountManager should not return null",AccountManager.getAccountManager());
    }

    @Test
    public void testGetCurrentAccountID()
    {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        assertEquals("currentAccount's id should be the the acccount at index 0's number",am.getAllAccounts().get(0).getId(),am.getCurrentAccountID());
    }

    @Test
    public void testCreateAccount() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        assertEquals("Ensure the method returns an object of type: Account", Class.forName("io.froilanandfriends.atm.Account"), am.getAllAccounts().get(0).getClass());
        am.createAccount(AccountType.BUSINESS);
        assertEquals("Ensure the method sets new Account to 'currentAccount", am.getCurrentAccount(), am.getAllAccounts().get(1));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        assertEquals("Should return the correct AccountType, CHECKING at index 0 ", AccountType.CHECKING, am.getAllAccounts().get(0).getAccountType());
        am.createAccount(AccountType.SAVINGS);
        am.deleteAccount(am.getAllAccounts().get(0).getId());
        assertEquals("Should return the correct SAVINGS, since the CHECKING at index 0 was deleted", AccountType.SAVINGS, am.getAllAccounts().get(0).getAccountType());
    }

    @Test
    public void testSwitchAccount() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        am.createAccount(AccountType.BUSINESS);
        assertEquals("Ensure the method sets last-made new Account to 'currentAccount", am.getCurrentAccount(), am.getAllAccounts().get(1));
        am.switchAccount(am.getAllAccounts().get(0).getId());
        assertEquals("Ensure the method switches 'currentAccount' to CHECKING ", am.getCurrentAccount(), am.getAllAccounts().get(0));

    }

    @Test
    public void testGetAccount() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        am.createAccount(AccountType.BUSINESS);
        assertEquals("Ensure getAccount method returns correct account", am.getAccount(am.getAllAccounts().get(0).getId()), am.getAllAccounts().get(0));
        assertEquals("Ensure getAccount method returns correct account", am.getAccount(am.getAllAccounts().get(1).getId()), am.getAllAccounts().get(1));

    }

    @Test
    public void testWithdrawl() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        am.deposit(50.00d);
        assertEquals("account at id 0 should now have a balance of 50.00d",50.00d,am.getAllAccounts().get(0).getBalance(),0.01d);
        am.withdrawl(20.00d);
        assertEquals("account at id 0 should now have a balance of 30.00d",30.00d,am.getAllAccounts().get(0).getBalance(),0.01d);
    }

    @Test
    public void testDeposit() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        assertEquals("account at id 0 should have a balance of 0.00d",0.00d,am.getAllAccounts().get(0).getBalance(),0.01d);
        am.deposit(25.00d);
        assertEquals("account at id 0 should now have a balance of 25.00d",25.00d,am.getAllAccounts().get(0).getBalance(),0.01d);
        am.deposit(15.00d);
        assertEquals("account at id 0 should now have a balance of 40.00d",40.00d,am.getAllAccounts().get(0).getBalance(),0.01d);
    }

    @Test
    public void testTransfer() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        am.deposit(25.00d);
        assertEquals("account at id 0 should now have a balance of 25.00d",25.00d,am.getAllAccounts().get(0).getBalance(),0.01d);
        am.createAccount(AccountType.SAVINGS);
        am.deposit(50.00d);
        assertEquals("account at id 1 should now have a balance of 50.00d",50.00d,am.getAllAccounts().get(1).getBalance(),0.01d);
        am.transfer(am.getAllAccounts().get(0).getId(),10.00d);
        assertEquals("account with id 0 should now have 35.00d",35.00d,am.getAllAccounts().get(0).getBalance(),0.01d);
        assertEquals("account with id 1 should now have 40.00d",40.00d,am.getAllAccounts().get(1).getBalance(),0.01d);
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        AccountManager am = new AccountManager();
        am.createAccount(AccountType.CHECKING);
        am.createAccount(AccountType.SAVINGS);
        am.createAccount(AccountType.BUSINESS);
        am.createAccount(AccountType.CHECKING);
        assertEquals("getAllaccounts should return an arraylist of size 4",4,am.getAllAccounts().size());
        assertEquals("account with the id at index 2 of the list should be of type BUSINESS",AccountType.BUSINESS,am.getAllAccounts().get(2).getAccountType());
    }
}