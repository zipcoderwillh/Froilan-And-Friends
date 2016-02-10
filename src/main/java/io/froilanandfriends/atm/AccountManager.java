package io.froilanandfriends.atm;

import java.util.ArrayList;

public class AccountManager {


    private ArrayList<Account> allAccounts = new ArrayList<Account>();
    private Account currentAccount;
    private static final String PATHNAME = "accountLog.csv";
    //Singleton Setup
    private static AccountManager current = new AccountManager();
    public static AccountManager getAccountManager(){
        return current;
    }

    /**** FILEIO  ***/
    //load in accounts passed from IO
    public void loadAccounts() throws Exception {
        String bigInputString = FileIO.readRecords(PATHNAME);
        String[] lineArray = bigInputString.split("\n");
        for (String accountLine: lineArray) {
            allAccounts.add(new Account(accountLine));
        }
    }
    // logs out the array of accounts, allAccounts, to a file specified by PATHNAME
    public void logAccounts() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (Account account: allAccounts) {
            String accountType = account.getAccountType().toString() + ",";
            String accountID = account.getId() + "\n";
            stringBuilder.append(accountType);
            stringBuilder.append(accountID);
        }
        String accountsToString = stringBuilder.toString();
        FileIO.logRecords(accountsToString, PATHNAME);
    }
    public void LogOut()
    {
        currentAccount = null;
    }
    /**** FILEIO ****/

    public void createAccount(AccountType accountType){
        //create account and add it to the list of accounts
        Account account = new Account(accountType);
        allAccounts.add(account);
        //set currentAccount to the one we just made
        currentAccount = account;
    }
    public void deleteAccount(long accountIDtoDelete){
        //loop through all accounts and remove the one with a matching id
        for(int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getId() == accountIDtoDelete ) {
                allAccounts.remove(i);
            }
        }
        currentAccount = null;
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
        //TransactionManager.getTransactionManager().createTransaction(TransactionType.WITHDRAWL,-1,currentAccount.getId(),amountToWithdrawl);
    }
    public void deposit( double amountToDeposit){
        //add given money to the current account
        currentAccount.deposit(amountToDeposit);
        //TransactionManager.getTransactionManager().createTransaction(TransactionType.DEPOSIT, currentAccount.getId(),-1,amountToDeposit);
    }
    public void transfer(long accountNumber, double amountToTransfer){
        //if the target account number is wrong, do nothing
        if (getAccount(accountNumber) == null)
        {
            return;
        }
        //otherwise withdraw from the current account
        currentAccount.withdraw(amountToTransfer);
        //and add to the target account
        getAccount(accountNumber).deposit(amountToTransfer);
        //TransactionManager.getTransactionManager().createTransaction(TransactionType.TRANSFER,accountNumber,currentAccount.getId(),amountToTransfer);
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


}
