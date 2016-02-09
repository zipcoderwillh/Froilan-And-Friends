package io.froilanandfriends.atm;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;

public class Account {
    private AccountType accountType;
    private long id;
    private double balance;
    private ArrayList<Integer> userIDs = new ArrayList<Integer>();
    Account(AccountType accountType){
        //set the account type to the given type
        this.accountType=accountType;
        //give each account a unique number
        //based on the current nano seconds
        this.id = System.nanoTime();
    }
    //secondary constructor for strings read in from file
    Account(String details)
    {

    }
    //remove money from this account
    public void withdraw(double amountToWithdraw){
        balance-=amountToWithdraw;
    }
    //add money to this account
    public void deposit(double amountToDeposit){
        balance+=amountToDeposit;
    }
    //return this account type
    public AccountType getAccountType() {
        return accountType;
    }
    //return this account's id
    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Integer> getUserIDs() {
        return userIDs;
    }
}

enum AccountType{
    CHECKING,SAVINGS,BUSINESS
}
