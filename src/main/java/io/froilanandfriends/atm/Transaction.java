package io.froilanandfriends.atm;

import java.util.Date;

/**
 * Class for individual transaction objects. Any interaction involving money at the ATM
 * will generate an instance of this class.
 */


public class Transaction {

    /**
     * Date of transaction.
     */
    private Date date;

    /**
     * Account IDs for destination and source accounts.
     */
    private long fromAccount,toAccount;

    /**
     * Amount of transaction.
     */
    private double amount;

    /**
     * Unique transaction ID.
     */
    private long id;

    /**
     * Current transaction type.
     */
    TransactionType transactionType;

    /**
     * <h1>Constructor Summary</h1>
     */

    /**
     * Will take args for every member field of Transaction. Whenever we load from
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
     * Used for deposits and transfers.
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
     * Used for withdrawls.
     * @param mType <code> TransactionType</code>
     * @param mFromAccount <code> long</code>
     * @param mAmount <code> double</code>
     * @param mId <code> long</code>
     */

    public Transaction(TransactionType mType, long mFromAccount, double mAmount, long mId){

        this(mType, mFromAccount, -1, mAmount, mId);
    }

    /**
     * Getter method for date of transaction.
     * @return Date, date object of the instance.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter method for source of transaction.
     * @return long, fromAccount of transaction
     */
    public long getFromAccount() {
        return fromAccount;
    }

    /**
     *Getter method for destination of transaction.
     * @return long, toAccount of transaction
     */
    public long getToAccount() {
        return toAccount;
    }

    /**
     * Getter method for amount of transaction.
     * @return double, amount of transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Getter method for ID of transaction.
     * @return long, id of transaction
     */
    public long getId() {
        return id;
    }

    /**
     *Getter method for type of transaction.
     * @return Transaction Type, enum representing transactionType
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Setter method to set date of transaction.
     * @param date a Date object
     * @return void
     */
    public void setDate(Date date) {
        this.date = date;
    }


}

/**
 * Represents the various types of transactions.
 */
enum TransactionType{
    WITHDRAWL,DEPOSIT,TRANSFER
}
