package io.froilanandfriends.atm;

import java.util.Date;

public class Transaction {
    //dateTime
    private Date date;
    private long fromAccount,toAccount;
    private double amount;
    private long id;
    private static long transactionIdCount = 0;
    TransactionType transactionType;

    Transaction(TransactionType mType, long mFromAccount, long mToAccount, double mAmount){
        transactionType = mType;
        fromAccount = mFromAccount;
        toAccount = mToAccount;
        amount = mAmount;
        //Set date to current time
        setDate(new Date());
        //Set id equal to last Transaction id + 1;
        setId();
    }

    Transaction(TransactionType mType, long mFromAccount, double mAmount){
        this(mType, mFromAccount, -1, mAmount);
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


    private void setId() {
        this.id = transactionIdCount;
        transactionIdCount++;
    }

}

enum TransactionType{
    WITHDRAWL,DEPOSIT,TRANSFER
}
