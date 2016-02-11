package io.froilanandfriends.atm;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;

/**
 * <h1>Abstract Class Account</h1>
 * <p>Class to hold accounts</p>
 */
public abstract class Account {
    protected AccountType accountType;
    protected long id;
    protected double balance;
    protected ArrayList<Integer> userIDs = new ArrayList<Integer>();

    //Standard Constructor - takes no args, initializes Account with subclass type setter, id with superclass id setter,
    //balance of zero, and first user in userids array as current users user id.

    /**
     * Account constructor, called in sub-classes
     */
    public Account(){
        //Give this account a unique id based on the nanosecond
        this.id = System.nanoTime();
        if (UserManager.getUserManager().getCurrentUser() != null) {
            userIDs.add(UserManager.getUserManager().getCurrentUser().getUserID());
        }
    }


    /**
     * Secondary constructor that generates an account based on a string from an account record
     * called from sub-classes
     * @param bigString the string representation of the account
     */
    Account(String bigString){
        String[] accountFields = bigString.split(",");
        String acctType = accountFields[0].toLowerCase();
        if(acctType.equals(AccountType.CHECKING.toString().toLowerCase())){
            this.accountType = AccountType.CHECKING;
        }else if(acctType.equals(AccountType.SAVINGS.toString().toLowerCase())){
            this.accountType = AccountType.SAVINGS;
        }else {
            this.accountType = AccountType.BUSINESS;
        }
        this.id = Long.parseLong(accountFields[1].trim());
        this.balance = Double.parseDouble(accountFields[2].trim());

        userIDs.add(0,Integer.parseInt(accountFields[3].trim()));

        //Check size of input string lines - for every record after the fourth will be extra users in account.
        int extraUsers = accountFields.length - 3;
        for(int i = 1; i<extraUsers; i++ ){
            userIDs.add(i,Integer.parseInt(accountFields[i+3]));
        }


    }

    /**
     * Adds the user with given id to this account
     * @param userID the user id to add to the account
     */
    public void addUserID(int userID){userIDs.add(userID);}

    /**
     * Removes the given money from the account
     * @param amountToWithdraw the amount of money to remove
     */
    public void withdraw(double amountToWithdraw){
        balance-=amountToWithdraw;
    }

    /**
     * Adds the given amount to this account
     * @param amountToDeposit the amount of money add
     */
    //add money to this account
    public void deposit(double amountToDeposit){
        balance+=amountToDeposit;
    }

    /**
     * returns this account's type
     * @return the type of account thsi is
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * returns this account's id
     * @return this account's id
     */
    public long getId() {
        return id;
    }

    /**
     * Returns this account's balance
     * @return the balance in this account
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the userIDs linked with this account
     * @return list of userIDs associated with this account
     */
    public ArrayList<Integer> getUserIDs() {
        return userIDs;
    }

    /**
     * Enum type retresenting the type of account
     */
    public enum AccountType{
        CHECKING,SAVINGS,BUSINESS
    }
}


