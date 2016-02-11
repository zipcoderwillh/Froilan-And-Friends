package io.froilanandfriends.atm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AccountManager {


    private ArrayList<Account> allAccounts = new ArrayList<Account>();
    private Account currentAccount;
    private static String PATHNAME = "accountLog.csv";
    //Singleton Setup
    private static AccountManager current = new AccountManager();
    public static AccountManager getAccountManager(){
        return current;
    }

    public static void setPATHNAME(String PATHNAME) {
        AccountManager.PATHNAME = PATHNAME;
    }

    /**** FILEIO  ***/
    //load in accounts passed from IO
    public void loadAccounts() throws Exception {
        String bigInputString = FileIO.readRecords(PATHNAME);
        String[] lineArray = bigInputString.split("\n");
        for (String loadString: lineArray) {
            createAccount(loadString);
        }
    }
    // logs out the array of accounts, allAccounts, to a file specified by PATHNAME
    public void logAccounts() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (Account account: allAccounts) {
            String accountType = account.getAccountType().toString() + ",";
            String accountID = Long.toString(account.getId())+",";
            String accountBalance = Double.toString(account.getBalance()) + ",";
            String userID = account.getUserIDs().get(0).toString();
            stringBuilder.append(accountType);
            stringBuilder.append(accountID);
            stringBuilder.append(accountBalance);

            //If only one user ID exists,
            if(account.getUserIDs().size() == 1 ){
                stringBuilder.append(userID).append("\n");
            } else {
                stringBuilder.append(userID).append(",");
                int extraUsers = account.userIDs.size() - 4;
                for (int i = 1; i < extraUsers-1; i++) {
                    stringBuilder.append(account.userIDs.get(i).toString()).append(",");
                }
                stringBuilder.append(account.userIDs.get(account.userIDs.size()-1)).append("\n");
            }



        }
        String accountsToString = stringBuilder.toString();
        FileIO.logRecords(accountsToString, PATHNAME);
    }
    /**** FILEIO ****/
    //Account factory
    public Account createAccount(AccountType accountType){
        Account newAccount;
        switch (accountType) {
            case BUSINESS:
                newAccount = new BusinessAccount();
                break;
            case CHECKING:
                newAccount = new CheckingAccount();
                break;
            case SAVINGS:
                newAccount = new SavingsAccount();
                break;
            default:
                return null;
        }
        //add new account to the accounts list
        allAccounts.add(newAccount);
        //set currentAccount to the one we just made
        currentAccount = newAccount;

        //Log all accounts
        try {
            logAccounts();
        }
        catch (Exception e)
        {
            System.out.println("ERROR: Could not log accounts.");
        }
        return newAccount;
    }

    public Account createAccount(String loadString)
    {
        Account newAccount;
        switch (loadString.substring(0,loadString.indexOf(',')).toUpperCase())
        {
            case "SAVINGS":
                newAccount = new BusinessAccount(loadString);
                break;
            case "CHECKING":
                newAccount = new CheckingAccount(loadString);
                break;
            case "BUSINESS":
                newAccount = new BusinessAccount(loadString);
                break;
            default:
                return null;
        }

        //add new account to the accounts list
        allAccounts.add(newAccount);
        //set currentAccount to the one we just made
        currentAccount = newAccount;
        return newAccount;

    }

    public void deleteAccount(long accountIDtoDelete){
        //loop through all accounts and remove the one with a matching id
        for(int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getId() == accountIDtoDelete ) {
                allAccounts.remove(i);
            }
        }
        //clear out current account;
        currentAccount = null;
        //re log all the accounts
        try {
            logAccounts();
        }
        catch (Exception e)
        {
            System.out.println("ERROR: Could not log accounts.");
        }
    }
    public void switchAccount(long accountIDtoSwitchTo){
        //set current account to one with the given id
        currentAccount = getAccount(accountIDtoSwitchTo);
    }

    public void clearCurrentAccount()
    {
        currentAccount = null;
    }

    public long getCurrentAccountID()
    {
        //return the id of the current account
        return currentAccount.getId();
    }
    public Account getAccount(long accountIDtoGet) {
        Account acc = null;
        //loop through and assign acc to the matching account
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getId() == accountIDtoGet)
                acc = allAccounts.get(i);
        }
        //if nothing matches the account, return null;
        return acc;
    }
    public void withdrawl(double amountToWithdrawl){
        //remove given money from the current account
        currentAccount.withdraw(amountToWithdrawl);
        TransactionManager.getTransactionManager().createTransaction(TransactionType.WITHDRAWL,currentAccount.getId(),amountToWithdrawl);
        try {
            logAccounts();
        }
        catch (Exception e)
        {
            System.out.println("ERROR: Could not log accounts.");
        }
    }
    public void deposit( double amountToDeposit){
        //add given money to the current account
        currentAccount.deposit(amountToDeposit);
        TransactionManager.getTransactionManager().createTransaction(TransactionType.DEPOSIT, currentAccount.getId(),amountToDeposit);
        try {
            logAccounts();
        }
        catch (Exception e)
        {
            System.out.println("ERROR: Could not log accounts.");
        }
    }
    public void transfer(long accountNumber, double amountToTransfer) {
        //if the target account number is wrong, do nothing
        if (getAccount(accountNumber) == null)
        {
            return;
        }
        //otherwise withdraw from the current account
        currentAccount.withdraw(amountToTransfer);
        //and add to the target account
        getAccount(accountNumber).deposit(amountToTransfer);
        TransactionManager.getTransactionManager().createTransaction(TransactionType.TRANSFER,accountNumber,currentAccount.getId(),amountToTransfer);
        try {
            logAccounts();
        }
        catch (Exception e)
        {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    public ArrayList<Account> getCurrentUsersAccounts(){
        UserManager um = UserManager.getUserManager();
        User currentUser = um.getCurrentUser();
        int currentUserID = currentUser.getUserID();
        ArrayList<Account> usersAccounts = new ArrayList<Account>();
        for(int x=0;x<allAccounts.size();x++){
            Account thisAccount = allAccounts.get(x);
            ArrayList<Integer> accountsUserIDs = thisAccount.getUserIDs();
            for(Integer z : accountsUserIDs){
                if(z==currentUserID){
                    usersAccounts.add(thisAccount);
                }
            }
        }
        return usersAccounts;
    }

    public ArrayList<Account> getAllAccounts() {
        //return the whole list of accounts
        return allAccounts;
    }

    public Account getCurrentAccount() {
        //return the current account
        return currentAccount;
    }

    public void addUserToCurrentAccount(int userID) {
        currentAccount.getUserIDs().add(userID);
    }
    public void addUserToCurrentAccount(User user) {
        addUserToCurrentAccount(user.getUserID());
    }

    public void removeUserFromCurrentAccount(int userID) {
        int toRemove = -1;
        for (int i = 0;i < currentAccount.getUserIDs().size();i++)
        {
            if (currentAccount.getUserIDs().get(i) == userID)
            {
                toRemove = i;
            }
        }
        if (toRemove >= 0)
        {
            currentAccount.getUserIDs().remove(toRemove);
        }
    }
    public void removeUserFromCurrentAccount(User user) {
        removeUserFromCurrentAccount(user.getUserID());
    }
}
