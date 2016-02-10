package io.froilanandfriends.atm;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;

public abstract class Account {
    protected AccountType accountType;
    protected long id;
    protected double balance;
    protected ArrayList<Integer> userIDs = new ArrayList<Integer>();
    Account(){
        //Give this account a unique id based on the nanosecond
        this.id = System.nanoTime();
    }
    //secondary constructor for strings read in from file
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
