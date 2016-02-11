package io.froilanandfriends.atm;

import java.util.Date;

public class Transaction {
    //dateTime
    private Date date;
    private long fromAccount,toAccount;
    private double amount;
    private long id;
    //private static long transactionIdCount = 0;
    TransactionType transactionType;


    /*** This is the constructor we will use to load transaction objects from history***/
    //Transaction constructor 1.
    Transaction(TransactionType mType, long mFromAccount, long mToAccount, double mAmount, Date date, long id){
        transactionType = mType;
        fromAccount = mFromAccount;
        toAccount = mToAccount;
        amount = mAmount;
        //Set date to time when previous transaction took place
        this.date = date;
        //Set id to id of old transaction
        this.id = id;
    }
    // Transaction constructor 2.
    Transaction(TransactionType mType, long mFromAccount, long mToAccount, double mAmount, long mId){
        transactionType = mType;
        fromAccount = mFromAccount;
        toAccount = mToAccount;
        amount = mAmount;
        //Set date to current time
        setDate(new Date());
        //Set id equal to last Transaction id + 1;
        id = mId;
    }
    //Transaction constructor 3.
    Transaction(TransactionType mType, long mFromAccount, double mAmount, long mId){

        this(mType, mFromAccount, -1, mAmount, mId);
    }

    public Date getDate() {
        return date;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public long getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}

enum TransactionType{
    WITHDRAWL,DEPOSIT,TRANSFER
}
