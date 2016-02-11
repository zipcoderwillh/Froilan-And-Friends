package io.froilanandfriends.atm;

import java.util.Date;

/**
 * <h1>Class Transaction</h1>
 */


public class Transaction {

    /**
     * public class for transaction objects. Any interaction involving money at the ATM
     * will generate an instance of this class
     */





    private Date date;
    private long fromAccount,toAccount;
    private double amount;
    private long id;
    TransactionType transactionType;

    /**
     * <h1>Constructor Summary</h1>
     */

    /**
     * Constructor
     * This constructor will take args for every member field of Transaction. Whenever we load from
     * our data file, this constructor will be used.
     * @param mType TransactionType
     * @param mFromAccount long
     * @param mToAccount long
     * @param mAmount double
     * @param date Date
     * @param id long
     */
    public Transaction(TransactionType mType, long mFromAccount, long mToAccount, double mAmount, Date date, long id){
        transactionType = mType;
        fromAccount = mFromAccount;
        toAccount = mToAccount;
        amount = mAmount;
        //Set date to time when previous transaction took place
        this.date = date;
        //Set id to id of old transaction
        this.id = id;
    }

    /**
     * Constructor used for deposits and transfers
     * @param mType <code>TransactionType </code>
     * @param mFromAccount <code>long</code>
     * @param mToAccount <code> long </code>
     * @param mAmount <code> double</code>
     * @param mId <code> long</code>
     */
    public Transaction(TransactionType mType, long mFromAccount, long mToAccount, double mAmount, long mId){
        transactionType = mType;
        fromAccount = mFromAccount;
        toAccount = mToAccount;
        amount = mAmount;
        //Set date to current time
        setDate(new Date());
        //Set id equal to last Transaction id + 1;
        id = mId;
    }

    /**
     * Constructor used for withdrawls.
     * @param mType <code> TransactionType</code>
     * @param mFromAccount <code> long</code>
     * @param mAmount <code> double</code>
     * @param mId <code> long</code>
     */

    public Transaction(TransactionType mType, long mFromAccount, double mAmount, long mId){

        this(mType, mFromAccount, -1, mAmount, mId);
    }

    /**
     * <h1>Method Summary</h1>
     */

    /**
     *
     * @return Date, date object of the instance
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @return long, fromAccount of transaction
     */
    public long getFromAccount() {
        return fromAccount;
    }

    /**
     *
     * @return long, toAccount of transaction
     */
    public long getToAccount() {
        return toAccount;
    }

    /**
     *
     * @return double, amount of transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     *
     * @return long, id of transaction
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return Transaction Type, enum representing transactionType
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     *
     * @param date a Date object
     * @return void
     */
    public void setDate(Date date) {
        this.date = date;
    }


}

enum TransactionType{
    WITHDRAWL,DEPOSIT,TRANSFER
}
