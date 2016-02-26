package io.froilanandfriends.atm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Wrapper class for all existing accounts. Also provides methods for creating, deleting, retrieving, and logging accounts.
 */
public class AccountManager {


    private ArrayList<Account> allAccounts = new ArrayList<Account>();
    private Account currentAccount;
    private static String PATHNAME = "accountLog.csv";
    //Singleton Setup
    private static AccountManager current = new AccountManager();

    /**
     * Returns the singleton instance of AccountManager.
     *
     * @return a singleton instance of AccountManager.
     */
    public static AccountManager getAccountManager() {
        return current;
    }

    private AccountFactory factory = new AccountFactory();

    /**
     * Sets the file path for the account record file.
     *
     * @param PATHNAME the pathname to the account record file.
     */
    public static void setPATHNAME(String PATHNAME) {
        AccountManager.PATHNAME = PATHNAME;
    }

    /**
     * Reads in all account records from FILEPATH.
     * Calls FileIO.readRecords, passing in PATHNAME.
     *
     * @throws Exception throws an exception if the file can't be read.
     */
    public void loadAccounts() throws Exception {
        String bigInputString = FileIO.readRecords(PATHNAME);
        String[] lineArray = bigInputString.split("\n");
        for (String loadString : lineArray) {
            createAccount(loadString);
        }
    }

    /**
     * Logs all the accounts to the file at FILEPATH.
     *
     * @throws Exception throws an exception if the file can't be written.
     */
    public void logAccounts() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (Account account : allAccounts) {
            String accountType = account.getAccountType().toString() + ",";
            String accountID = Long.toString(account.getId()) + ",";
            String accountBalance = Double.toString(account.getBalance()) + ",";
            String userID = account.getUserIDs().get(0).toString();
            stringBuilder.append(accountType);
            stringBuilder.append(accountID);
            stringBuilder.append(accountBalance);

            //If only one user ID exists,
            if (account.getUserIDs().size() == 1) {
                stringBuilder.append(userID).append("\n");
            } else {
                stringBuilder.append(userID).append(",");
                int extraUsers = account.userIDs.size() - 4;
                for (int i = 1; i < extraUsers - 1; i++) {
                    stringBuilder.append(account.userIDs.get(i).toString()).append(",");
                }
                stringBuilder.append(account.userIDs.get(account.userIDs.size() - 1)).append("\n");
            }


        }
        String accountsToString = stringBuilder.toString();
        FileIO.logRecords(accountsToString, PATHNAME);
    }

    /**
     * Creates an account and sets it as the currently selected account.
     * relies on an AccountFactory for account creation.
     * rewrites the log once hte new account is created.
     *
     * @param accountType the type of account that should be created.
     * @return the newly created account.
     */
    public Account createAccount(Account.AccountType accountType) {
        Account newAccount = factory.create(accountType);
        //add new account to the accounts list
        allAccounts.add(newAccount);
        //set currentAccount to the one we just made
        currentAccount = newAccount;

        //Log all accounts
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
        return newAccount;
    }

    /**
     * Creates an account based on a string from a record file. Relies on an AccountFactory to create the account. Does not update the log file,
     * as the created account was read in from the log.
     * @param loadString one line from the account record file.
     * @return returns the created account.
     */
    public Account createAccount(String loadString) {
        Account newAccount = factory.createFromRecord(loadString);
        //add new account to the accounts list
        allAccounts.add(newAccount);
        //set currentAccount to the one we just made
        currentAccount = newAccount;
        return newAccount;
    }

    /**
     * Deletes an account with id accountIDtoDelete if it exists.
     * Updates the log file once the account has been deleted.
     *
     * @param accountIDtoDelete the account id to delete.
     */
    public void deleteAccount(long accountIDtoDelete) {
        //loop through all accounts and remove the one with a matching id
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getId() == accountIDtoDelete) {
                allAccounts.remove(i);
            }
        }
        //clear out current account;
        currentAccount = null;
        //re log all the accounts
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    /**
     * Switches this AccountManager's currentAccount to accountIDtoSwitchTo.
     *
     * @param accountIDtoSwitchTo the account id to make the current account.
     */
    public void switchAccount(long accountIDtoSwitchTo) {
        //set current account to one with the given id
        currentAccount = getAccount(accountIDtoSwitchTo);
    }

    /**
     * Sets this AccountManager's currentAccount to null.
     */
    public void clearCurrentAccount() {
        currentAccount = null;
    }

    /**
     * Returns the id of this AccountManager's current account.
     *
     * @return the current account
     */
    public long getCurrentAccountID() {
        //return the id of the current account
        return currentAccount.getId();
    }

    /**
     * Getter method to retrieve account by ID number.
     * @param accountIDtoGet ID number of account to retrieve.
     * @return account with specified ID.
     */
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

    /**
     * Withdraws the given amount from the current account.
     * Logs the account record after the withdraw.
     *
     * @param amountToWithdrawl the amount to withdraw
     */
    public void withdrawl(double amountToWithdrawl) {
        //remove given money from the current account
        currentAccount.withdraw(amountToWithdrawl);
        TransactionManager.getTransactionManager().createTransaction(TransactionType.WITHDRAWL, currentAccount.getId(), amountToWithdrawl);
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    /**
     * Deposits the given amount into the current account.
     * Logs accounts after the money has been deposited.
     *
     * @param amountToDeposit the amount to deposit.
     */
    public void deposit(double amountToDeposit) {
        //add given money to the current account
        currentAccount.deposit(amountToDeposit);
        TransactionManager.getTransactionManager().createTransaction(TransactionType.DEPOSIT, currentAccount.getId(), amountToDeposit);
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    /**
     * Transfers the given amount from the current account into an account matching the given id.
     * Updates the account logs once the transfer has finished.
     *
     * @param accountNumber    the account id to transfer the funds INTO.
     * @param amountToTransfer the amouunt to transfer into account that matches the passed id.
     */
    public void transfer(long accountNumber, double amountToTransfer) {
        //if the target account number is wrong, do nothing
        if (getAccount(accountNumber) == null) {
            return;
        }
        //otherwise withdraw from the current account
        currentAccount.withdraw(amountToTransfer);
        //and add to the target account
        getAccount(accountNumber).deposit(amountToTransfer);
        TransactionManager.getTransactionManager().createTransaction(TransactionType.TRANSFER, accountNumber, currentAccount.getId(), amountToTransfer);
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    /**
     * Returns a list of accounts that the current user is linked with.
     *
     * @return a list containing the current user's accounts.
     */
    public ArrayList<Account> getCurrentUsersAccounts() {
        UserManager um = UserManager.getUserManager();
        User currentUser = um.getCurrentUser();
        int currentUserID = currentUser.getUserID();
        ArrayList<Account> usersAccounts = new ArrayList<Account>();
        for (int x = 0; x < allAccounts.size(); x++) {
            Account thisAccount = allAccounts.get(x);
            ArrayList<Integer> accountsUserIDs = thisAccount.getUserIDs();
            for (Integer z : accountsUserIDs) {
                if (z == currentUserID) {
                    usersAccounts.add(thisAccount);
                }
            }
        }
        return usersAccounts;
    }

    /**
     * Returns all the accounts in the system.
     *
     * @return a list containing all accounts.
     */
    public ArrayList<Account> getAllAccounts() {
        //return the whole list of accounts
        return allAccounts;
    }

    /**
     * Returns the currently selected account.
     *
     * @return the currently selected account.
     */
    public Account getCurrentAccount() {
        //return the current account
        return currentAccount;
    }

    /**
     * Adds the user that matches the given userID to the currently selected account.
     * Logs accounts after adding the user.
     *
     * @param userID the userID of the user to be linked with the account.
     */
    public void addUserToCurrentAccount(int userID) {
        currentAccount.getUserIDs().add(userID);
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    /**
     * Adds the given user object to hte currently selected account.
     * Logs accounts after adding the user.
     *
     * @param user the user to add to the current account.
     */
    public void addUserToCurrentAccount(User user) {
        addUserToCurrentAccount(user.getUserID());
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    /**
     * Removes the user matching the given id from the curreent account.
     * Logs accounts after removing.
     *
     * @param userID the userID of the user to rmeove from the current account.
     */
    public void removeUserFromCurrentAccount(int userID) {
        int toRemove = -1;
        for (int i = 0; i < currentAccount.getUserIDs().size(); i++) {
            if (currentAccount.getUserIDs().get(i) == userID) {
                toRemove = i;
            }
        }
        if (toRemove >= 0) {
            currentAccount.getUserIDs().remove(toRemove);
        }
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }

    /**
     * Removes the passed User object from the current account.
     * Logs accounts after removeing the user.
     *
     * @param user the user to remove from the account.
     */
    public void removeUserFromCurrentAccount(User user) {
        removeUserFromCurrentAccount(user.getUserID());
        try {
            logAccounts();
        } catch (Exception e) {
            System.out.println("ERROR: Could not log accounts.");
        }
    }
}
