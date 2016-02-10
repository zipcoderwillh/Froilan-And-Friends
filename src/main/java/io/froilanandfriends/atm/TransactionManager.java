package io.froilanandfriends.atm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionManager {

    private static final String PATHNAME = "transactionsLog.csv";
    private static ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();

    //Singleton Setup
    private static TransactionManager current = new TransactionManager();
    TransactionManager(){}
    public static TransactionManager getTransactionManager(){
        return current;
    }


    public void createTransaction(TransactionType type, long fromAccount,long toAccount , double amount){
        Transaction transaction = new Transaction(type, fromAccount, toAccount, amount);
        allTransactions.add(transaction);
    }

    //Overload for no to account. i.e Withdrawls
    public void createTransaction(TransactionType type, long fromAccount, double amount){
        Transaction transaction = new Transaction(type, fromAccount, amount);
        allTransactions.add(transaction);
    }

    /**
     loadTransactions() will call FileIO's readRecords method to pull up an array
     of strings, with each string representing a transaction object.
     we split each of these transaction object strings into it's constituent fields
     e.g. type, date, amount etc. and then call a custom transaction constructor which
     accepts these fields as arguments, and creates the corresponding transaction object.
     finally we add each object created to the allTransactions ArrayList.**/
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



            Transaction transaction = new Transaction(type, from, to, amount, date, id);
            allTransactions.add(transaction);

        }

    }

    /**at the end of a hard day of managing transactions
     transactionManager will log transactions to a persistent file
     named according to the member field PATHNAME, using FileIO's logRecords().It makes one big string in a foreach loop, where each line represents a transaction object and each column of that line represents a field of that object.**/
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


    //Returns the last transaction on the arraylist.
    public Transaction getLastTransaction(){
        return allTransactions.get(allTransactions.size()-1);
    }


    //Returns the entire arraylist of transactions
    public ArrayList<Transaction> getAllTransactions(){
        return allTransactions;
    }


    //Returns the transactions within the range.
    public ArrayList<Transaction> getRangeTransactions(Date from, Date to){
        ArrayList<Transaction> result = new ArrayList<Transaction>();

        for (Transaction transaction : allTransactions){
            //If transaction is within range. Add it to the arraylist
            if(transaction.getDate().getTime() >= from.getTime() && transaction.getDate().getTime() <= to.getTime())
                result.add(transaction);
        }

        return result;
    }


    public Transaction getSpecificTransaction(long ID){
        for (Transaction transaction : allTransactions){
            //If transaction has the expected ID. Return it
            if (transaction.getId() == ID)
                return transaction;

        }
        return null;
    }


    public ArrayList<Transaction> getCurrentAccountTransactions(long AccountId){
        //Get an account manager.
        AccountManager manager = AccountManager.getAccountManager();

        //TODO: Change to get current account. Method not yet implemented
        //Find the account requested.
        Account account = manager.getAccount(AccountId);

        //Create container for Transactions
        ArrayList<Transaction> accountTransactions = new ArrayList<Transaction>();

        //If account is found
        if(account != null){
            //Loop through all transactions
            for(Transaction trans : allTransactions){
                //If from account equals the users account number, add it to accountTransactions.
                if(trans.getFromAccount() == account.getId())
                    accountTransactions.add(trans);
            }
        }

        return accountTransactions;

    }

}
