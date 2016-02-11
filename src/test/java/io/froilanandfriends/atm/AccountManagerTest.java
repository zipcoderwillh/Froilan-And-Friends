package io.froilanandfriends.atm;

import jdk.nashorn.internal.runtime.ECMAException;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountManagerTest {

    AccountManager accountManager;
    UserManager userManager;

    @BeforeClass
    public static void setupUser() {
        UserManager.setPATHNAME("testLoadUserLog.csv");

        User user1 = new User("1", "Bob", "Bobby", "bob@aol.com", 1234, "What is?", "Yes");
        UserManager.getUserManager().setCurrentUser(user1);
        UserManager.setPATHNAME("userLog.csv");
    }

    @Before
    public void init() {
        UserManager.setPATHNAME("testLoadUserLog.csv");
        AccountManager.setPATHNAME("testAccountsLog.csv");

        accountManager = AccountManager.getAccountManager();
        userManager = UserManager.getUserManager();
        accountManager.createAccount(Account.AccountType.BUSINESS);
        accountManager.createAccount(Account.AccountType.CHECKING);
        accountManager.createAccount(Account.AccountType.SAVINGS);
        UserManager.setPATHNAME("userLog.csv");
        AccountManager.setPATHNAME("accountsLog.csv");

    }


    

    @Test
    public void testLoadAccounts() throws Exception {
        /*
         we set the PATHNAME to another file and write in a string formatted the same
         way any account string in the usual log file would be. If load accounts
         is successful there will be a account object made with the details supplied in
         that string, and it will be the last accounts in the  allAccount array. Test works
         like a charm

         AccountManager manager = AccountManager.getAccountManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");

         manager.loadAccounts();


         int last = manager.getAllAccounts().size() - 1;
         Account account =  manager.getAllAccounts().get(last);

         //assertTrue("BUSINESS".equals(account.getAccountType().toString()));

         assertEquals("last account in accountManager should have an id of 84645914125183",86793680569076L,account.getId());
         assertEquals("last account in accountManager should have a balance of 2000.00",2000.0d,account.getBalance(),0.01d);
         ArrayList<Integer> userIDs = account.getUserIDs();
         assertEquals("last account in accountManager should be linked to account 42",42, (int) userIDs.get(0));
         assertEquals("last account in accountManager should be linked to account 42",43, (int) userIDs.get(1));
        UserManager.setPATHNAME("userLog.csv");*/
    }

    @Test
    public void testLogAccounts() throws Exception {
        UserManager userManager = UserManager.getUserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        AccountManager.setPATHNAME("testAccountsLog.csv");

        //test to see if the file logAccounts is creating to write to
        //exists. it does. tested if the file is as many lines as we intended it
        //to be.
        accountManager.setPATHNAME("testAccountsLog.csv");
        userManager.addUser("dong", "s", "t", "e", 1234, "k", "l" );
        User user = userManager.getUser("dong");
        int userID = user.getUserID();
        userManager.addUser("flong","first","last","email",4321,"q","a");
        User user2 = userManager.getUser("flong");
        int userID2 = user2.getUserID();
        userManager.setCurrentUser(user);
        Account account = accountManager.createAccount(Account.AccountType.BUSINESS);
        account.addUserID(userID2);
        long accountID = accountManager.getCurrentAccountID();
        accountManager.logAccounts();
        File file = new File("testAccountsLog.csv");
        BufferedReader br = new BufferedReader(new FileReader("testAccountsLog.csv"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line + "\n");

        }
        String bigString = stringBuilder.toString().trim();
        String[] targetArr = bigString.split("\n");
        int last = targetArr.length - 1;
        String[] lastArr = targetArr[last].split(",");
        assertTrue(lastArr[0], lastArr[0].equals("BUSINESS"));
        System.out.println(userID);
        assertTrue(lastArr[1].equals(accountID + ""));
        assertTrue(lastArr[2].equals("0.0"));
        assertTrue(lastArr[3].equals(userID + ""));
        assertTrue(lastArr[4].equals(userID2+ ""));
        br.close();
        accountManager.setPATHNAME("accountLog.csv");
        UserManager.setPATHNAME("userLog.csv");
        AccountManager.setPATHNAME("accountsLog.csv");
    }


    @Test
    public void testGetAccountManager() throws Exception {
        assertNotNull("getaccountManager should not return null", AccountManager.getAccountManager());
    }
/*
    @Test
    public void testGetCurrentAccountID() {
        AccountManager am = AccountManager.getAccountManager();
        am.setPATHNAME("testLoadAccounts.csv");

        am.createAccount(Account.AccountType.CHECKING);
        assertEquals("currentAccount's id should be the the acccount at index 0's number", am.getAllAccounts().get(0).getId(), am.getCurrentAccountID());
        am.setPATHNAME("accountLog.csv");
    }
*/
    @Test
    public void testCreateAccount() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");
        am.createAccount(Account.AccountType.CHECKING);
        am.createAccount(Account.AccountType.BUSINESS);
        assertEquals("Ensure the method sets new Account to 'currentAccount", am.getCurrentAccount(), am.getAllAccounts().get(1));
        am.setPATHNAME("accountLog.csv");
        AccountManager.setPATHNAME("accountsLog.csv");
    }

    @Test
    public void testDeleteAccount() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.CHECKING);
        assertEquals("Should return the correct AccountType, CHECKING at index 0 ", Account.AccountType.CHECKING, am.getAllAccounts().get(0).getAccountType());
        am.createAccount(Account.AccountType.SAVINGS);
        am.deleteAccount(am.getAllAccounts().get(0).getId());
        assertEquals("Should return the correct SAVINGS, since the CHECKING at index 0 was deleted", Account.AccountType.SAVINGS, am.getAllAccounts().get(0).getAccountType());
        am.setPATHNAME("accountLog.csv");

    }

    @Test
    public void testSwitchAccount() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.CHECKING);
        am.createAccount(Account.AccountType.BUSINESS);
        assertEquals("Ensure the method sets last-made new Account to 'currentAccount", am.getCurrentAccount(), am.getAllAccounts().get(1));
        am.switchAccount(am.getAllAccounts().get(0).getId());
        assertEquals("Ensure the method switches 'currentAccount' to CHECKING ", am.getCurrentAccount(), am.getAllAccounts().get(0));
        am.setPATHNAME("accountLog.csv");
    }

    @Test
    public void testAddUserToCurrentAccount() {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        User user2 = new User("22", "Joe", "Joey", "joe@aol.com", 4321, "What is I?", "You");
        am.createAccount(Account.AccountType.BUSINESS);
        assertEquals("The account should now have a userID list with size 1", 1, am.getCurrentAccount().getUserIDs().size());
        am.addUserToCurrentAccount(user2);
        assertEquals("The account should now have a userID list with size 2", 2, am.getCurrentAccount().getUserIDs().size());
        am.removeUserFromCurrentAccount(user2);
        assertEquals("The account should now have a userID list with size 1", 1, am.getCurrentAccount().getUserIDs().size());
        am.setPATHNAME("accountLog.csv");
    }

    @Test
    public void testGetAccount() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.CHECKING);
        am.createAccount(Account.AccountType.BUSINESS);
        assertEquals("Ensure getAccount method returns correct account", am.getAccount(am.getAllAccounts().get(0).getId()), am.getAllAccounts().get(0));
        assertEquals("Ensure getAccount method returns correct account", am.getAccount(am.getAllAccounts().get(1).getId()), am.getAllAccounts().get(1));
        am.setPATHNAME("accountLog.csv");
    }

    @Test
    public void testWithdrawl() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.CHECKING);
        am.deposit(50.00d);
        assertEquals("account at id 0 should now have a balance of 50.00d", 50.00d, am.getAllAccounts().get(0).getBalance(), 0.01d);
        am.withdrawl(20.00d);
        assertEquals("account at id 0 should now have a balance of 30.00d", 30.00d, am.getAllAccounts().get(0).getBalance(), 0.01d);
        am.setPATHNAME("accountLog.csv");
    }

    @Test
    public void testDeposit() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.CHECKING);
        assertEquals("account at id 0 should have a balance of 0.00d", 0.00d, am.getAllAccounts().get(0).getBalance(), 0.01d);
        am.deposit(25.00d);
        assertEquals("account at id 0 should now have a balance of 25.00d", 25.00d, am.getAllAccounts().get(0).getBalance(), 0.01d);
        am.deposit(15.00d);
        assertEquals("account at id 0 should now have a balance of 40.00d", 40.00d, am.getAllAccounts().get(0).getBalance(), 0.01d);
        am.setPATHNAME("accountLog.csv");
    }

    @Test
    public void testTransfer() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.CHECKING);
        am.deposit(25.00d);
        assertEquals("account at id 0 should now have a balance of 25.00d", 25.00d, am.getAllAccounts().get(0).getBalance(), 0.01d);
        am.createAccount(Account.AccountType.SAVINGS);
        am.deposit(50.00d);
        assertEquals("account at id 1 should now have a balance of 50.00d", 50.00d, am.getAllAccounts().get(1).getBalance(), 0.01d);
        am.transfer(am.getAllAccounts().get(0).getId(), 10.00d);
        assertEquals("account with id 0 should now have 35.00d", 35.00d, am.getAllAccounts().get(0).getBalance(), 0.01d);
        assertEquals("account with id 1 should now have 40.00d", 40.00d, am.getAllAccounts().get(1).getBalance(), 0.01d);
        am.setPATHNAME("accountLog.csv");
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.CHECKING);
        am.createAccount(Account.AccountType.SAVINGS);
        am.createAccount(Account.AccountType.BUSINESS);
        am.createAccount(Account.AccountType.CHECKING);
        assertEquals("getAllaccounts should return an arraylist of size 4", 4, am.getAllAccounts().size());
        assertEquals("account with the id at index 2 of the list should be of type BUSINESS", Account.AccountType.BUSINESS, am.getAllAccounts().get(2).getAccountType());
        am.setPATHNAME("accountLog.csv");
    }

    @Test
    public void testGetCurrentUsersAccounts() throws Exception {
        UserManager.getUserManager().setCurrentUser(new User("1", "Bob", "Bobby", "bob@aol.com", 1234, "What is?", "Yes"));
        AccountManager am = new AccountManager();
        AccountManager.setPATHNAME("testAccountsLog.csv");

        am.createAccount(Account.AccountType.BUSINESS);
        User u2 = new User("22", "Joe", "Joey", "joe@aol.com", 4321, "What is I?", "You");
        am.getAllAccounts().get(0).getUserIDs().add(u2.getUserID());
        assertEquals("current account should be linked to two users", 2, am.getCurrentAccount().getUserIDs().size());
        am.setPATHNAME("accountLog.csv");
    }
}