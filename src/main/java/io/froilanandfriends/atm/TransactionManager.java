package io.froilanandfriends.atm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *     Manages Transactions in the form of a large ArrayList of all Transactions. Has methods
 *     to create Transactions and look specific ones up from ArrayList as needed. TransactionManager
 *     is instantiated only once as the singleton {@code current}
 */

public class TransactionManager {

    /**
     * Path to local storage of transaction logs.
     */
    private static String PATHNAME = "transactionsLog.csv";

    /**
     * List of all transactions.
     */
    private static ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();

    private static TransactionManager current = new TransactionManager();

    TransactionManager(){
    }

    /**
     * Returns the singleton {@Code current}
     * @return {@Code current}
     */
    public static TransactionManager getTransactionManager(){
        return current;
    }

    /**
     * Creates a Transaction object.
     * @param type TransactionType
     * @param fromAccount long
     * @param toAccount long
     * @param amount double
     */
    public void createTransaction(TransactionType type, long fromAccount,long toAccount , double amount){
        long id = setTransactionID();
        Transaction transaction = new Transaction(type, fromAccount, toAccount, amount, id);
        allTransactions.add(transaction);
        try{
            logTransactions();
        }catch (Exception e){

        }

    }

    //Calls transaction constructor 3. Overload for no to account. i.e Withdrawls.

    /**
     * Creates a transaction object with the custom Transaction
     * constructor designed to handle withdrawls.
     * @param type TransactionType
     * @param fromAccount long
     * @param amount double
     */
    public void createTransaction(TransactionType type, long fromAccount, double amount){
        long id = setTransactionID();
        Transaction transaction = new Transaction(type, fromAccount, amount, id);
        allTransactions.add(transaction);
        try{
            logTransactions();
        }catch (Exception e){

        }
    }

    /**
     * Creates unique TransactionID by adding the allTransactions ArrayList size
     * and an arbitrary integer (1234567). Uniqueness is guaranteed because
     * each time a transaction is made the ArrayList grows by 1.
     * @return long
     */
    public long setTransactionID(){ return allTransactions.size() + 1234567; }


    /**
     * sets the name of the file TransactionManager writes to or reads from
     * @param PATHNAME String
     */
    public static void setPATHNAME(String PATHNAME) {
        TransactionManager.PATHNAME = PATHNAME;
    }



    /**
     *  loadTransactions() will call FileIO's readRecords method to pull up an array
     *  of strings, with each string representing a transaction object.
     *  we split each of these transaction object strings into it's constituent fields
     *  e.g. type, date, amount etc. and then call a custom transaction constructor which
     *  accepts these fields as arguments, and creates the corresponding transaction object.
     *  finally we add each object created to the allTransactions ArrayList.
     * @throws Exception
     */
    public void loadTransactions() throws Exception {

        String[] transactionMembers = FileIO.readRecords(PATHNAME).split("\\n");
        for (String s: transactionMembers){
            String[] lineArr = s.split(",");

            String typeStr = lineArr[0].toLowerCase();

            TransactionType type;
            if (typeStr.equals(TransactionType.DEPOSIT.toString().toLowerCase())) {
                type = TransactionType.DEPOSIT;
            } else if (typeStr.equals(TransactionType.WITHDRAWL.toString().toLowerCase())) {
                type = TransactionType.WITHDRAWL;
            } else {
                type = TransactionType.TRANSFER;
            }

            long from = Long.parseLong(lineArr[1]);
            long to = Long.parseLong(lineArr[2]);
            double amount = Double.parseDouble(lineArr[3]);
            DateFormat format = new SimpleDateFormat("EEEE MMM dd kk:mm:ss zzz yyyy");
            Date date = format.parse(lineArr[4]);
            long id = Long.parseLong(lineArr[5]);


            //Calls transaction constructor 1.
            Transaction transaction = new Transaction(type, from, to, amount, date, id);
            allTransactions.add(transaction);

        }

    }


    /**
     * Using this method, TransactionManager will log transactions to a persistent file
     * named according to the member field PATHNAME, using FileIO's logRecords()
     * It makes one big string in a foreach loop, where each line represents a
     * transaction object and each column of that line represents a field of that object.
     * @throws Exception
     */
    public void logTransactions() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction t : allTransactions) {
            stringBuilder.append(t.getTransactionType().toString() + ",");
            stringBuilder.append(t.getFromAccount() + ",");
            stringBuilder.append(t.getToAccount() + ",");
            stringBuilder.append(t.getAmount() + ",");
            stringBuilder.append(t.getDate().toString() + ",");
            stringBuilder.append(t.getId() + "\n");
        }

        FileIO.logRecords(stringBuilder.toString(), PATHNAME);
    }


    /**
     * Returns last transaction on the ArrayList allTransactions
     * @return last Transaction
     */
    public Transaction getLastTransaction(){
        return allTransactions.get(allTransactions.size()-1);
    }



    /**
     * Returns the ArrayList allTransactions
     * @return allTransactions ArrayList
     */
    public ArrayList<Transaction> getAllTransactions(){
        return allTransactions;
    }


    //Returns the transactions within the range.

    /**
     * Returns the transactions from allTransactions within a range
     * specified by two Date objects passed as parameters
     * @param from Date
     * @param to Date
     * @return result ArrayList
     */
    public ArrayList<Transaction> getRangeTransactions(Date from, Date to){
        ArrayList<Transaction> result = new ArrayList<Transaction>();

        for (Transaction transaction : allTransactions){
            //If transaction is within range. Add it to the arraylist
            if(transaction.getDate().getTime() >= from.getTime() && transaction.getDate().getTime() <= to.getTime())
                result.add(transaction);
        }

        return result;
    }

    /**
     * Finds and returns a transaction by a given ID.
     * If no such transaction exists, returns null.
     * @param ID long
     * @return transaction Transaction
     */
    public Transaction getSpecificTransaction(long ID){
        for (Transaction transaction : allTransactions){
            //If transaction has the expected ID. Return it
            if (transaction.getId() == ID)
                return transaction;

        }
        return null;
    }

    /**
     * Returns an ArrayList of all the transactions of the currentAccount,
     * where currentAccount is given by the account the current user is viewing
     * at the current moment
     * @return currentAccountTransactions ArrayList
     */
    public ArrayList<Transaction> getCurrentAccountTransactions(){
        //Get an account manager.
        AccountManager manager = AccountManager.getAccountManager();

        //Find the account requested.
        Account account = manager.getCurrentAccount();


        //Create container for Transactions
        ArrayList<Transaction> accountTransactions = new ArrayList<Transaction>();
        long currentAccountId = account.getId();
        //If account is found
        if(account != null){
            //Loop through all transactions
            for(Transaction trans : allTransactions){
                //If from account equals the users account number, add it to accountTransactions.
                if(trans.getFromAccount() == currentAccountId||trans.getToAccount()==currentAccountId)
                    accountTransactions.add(trans);
            }
        }

        return accountTransactions;

    }

}
