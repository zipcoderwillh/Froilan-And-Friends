package io.froilanandfriends.atm;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;

/**
 * Represents an individual account. Superclass for other account types.
 * @see AccountManager().
 *
 */
public abstract class Account {

    /**
     * Indicates the type of the account (such as checking, savings, etc.).
     */
    protected AccountType accountType;

    /**
     * Unique identifier for this account.
     */
    protected long id;

    /**
     * The amount of money currently in the account.
     */
    protected double balance;

    /**
     * Users are be able to share accounts, in other words, each account can
     * have more than one userID affiliated with it. Hence we need an arrayList
     * to hold multiple IDs. The user who creates the account will always
     * be at index zero in the array.
     *
     * **/
    protected ArrayList<Integer> userIDs = new ArrayList<Integer>();

    //Standard Constructor - takes no args, initializes Account with subclass type setter, id with superclass id setter,
    //balance of zero, and first user in userids array as current users user id.

    /**
     * Account constructor, called in sub-classes.
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
     * called from sub-classes.
     * @param bigString the string representation of the account.
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
     * Adds the user with given id to this account.
     * @param userID the user id to add to the account.
     */
    public void addUserID(int userID){userIDs.add(userID);}

    /**
     * Removes the given amount of money from the account.
     * @param amountToWithdraw the amount of money to remove.
     */
    public void withdraw(double amountToWithdraw){
        balance-=amountToWithdraw;
    }

    /**
     * Adds the given amount of money to this account.
     * @param amountToDeposit the amount of money add.
     */
    //add money to this account
    public void deposit(double amountToDeposit){
        balance+=amountToDeposit;
    }

    /**
     * Getter method for this account's type.
     * @return the type of this account.
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Getter method for this account's id.
     * @return this account's id.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns this account's balance.
     * @return the balance in this account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the userIDs linked with this account.
     * @return list of userIDs associated with this account.
     */
    public ArrayList<Integer> getUserIDs() {
        return userIDs;
    }

    /**
     * Enum type representing the type of account.
     */
    public enum AccountType{
        CHECKING,SAVINGS,BUSINESS
    }
}


