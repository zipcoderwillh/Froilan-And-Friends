package io.froilanandfriends.atm;

import java.util.ArrayList;
import java.util.Date;

public class TransactionManager {

    private static ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();

    //Singleton Setup
    private static TransactionManager current = new TransactionManager();
    TransactionManager(){}
    public static TransactionManager getTransactionManager(){
        return current;
    }


    public void createTransaction(TransactionType type, long toAccount, long fromAccount, double amount){
        Transaction transaction = new Transaction(type, fromAccount, toAccount, amount);
        allTransactions.add(transaction);
    }

    //Overload for no to account. i.e Withdrawls
    public void createTransaction(TransactionType type, long fromAccount, double amount){
        Transaction transaction = new Transaction(type, fromAccount, amount);
        allTransactions.add(transaction);
    }

    public void loadTransactions(){
        //calls settransaction from fileIO, which sends back a huge string you need to iterate over
        try{
            String transactionsFeed =  FileIO.setTransactions().trim();

            //{type,toAccount,fromAccount,Ammount}

            String[] items = transactionsFeed.split(",");


            for(int i = 0; i<items.length; i++){


            }

        }catch (Exception e){

        }

        //iterates over string to populate allTransactions

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
