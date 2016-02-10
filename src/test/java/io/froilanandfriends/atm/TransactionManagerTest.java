package io.froilanandfriends.atm;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Doc on 2/9/16.
 */
public class TransactionManagerTest {
    public static final double EXPECTED_AMOUNT = 200.00;
    @Test
    public void testGetTransactionManager() throws Exception {
        TransactionManager manager = TransactionManager.getTransactionManager();

        assertNotNull(manager);
    }

    @Test
    public void testCreateTransaction() throws Exception {
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.createTransaction(TransactionType.TRANSFER, 1, 2, 200.00);

        Transaction transaction = manager.getLastTransaction();

        assertEquals(EXPECTED_AMOUNT, transaction.getAmount(), .2);

    }

    @Test
    public void testCreateTransaction1() throws Exception {
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.createTransaction(TransactionType.WITHDRAWL, 1, 200.00);

        Transaction transaction = manager.getLastTransaction();

        assertEquals(-1, transaction.getToAccount());
    }

    @Test
    public void testLoadTransactions() throws Exception {
        /****
         loadTransactions looks in the file transactionsLog.csv
         and makes a transaction object with each line of the file, and stores each in
         manager's transactions array
         The test runs successfully when the to-account # we read from line 1 is the same
         as the to-account of transaction object at index 0 if the array. There is something a bit
         wonky right now because one of the factory's in transactionManager makes the accounts in an order
         different than normal.
         ****/
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.loadTransactions();
        Transaction transaction = manager.getAllTransactions().get(0);
        assertEquals(1899888, transaction.getToAccount());

    }

    @Test
    public void testLogTransactions() throws Exception {
        /** test shows that after we stock manager's transaction array, logTransactions()
         * can take those objects create a long string with them and write it to
         * a file. The test shows the file exists and we can see the contents are in order
         * when we print out
         **/
        TransactionManager manager = TransactionManager.getTransactionManager();
        //                                        type      to      from    amount
        manager.createTransaction(TransactionType.DEPOSIT, 1899888, 1888181, 80000);
        manager.createTransaction(TransactionType.DEPOSIT, 1899888, 1888181, 80000);
        manager.createTransaction(TransactionType.DEPOSIT, 1899888, 1888181, 80000);
        manager.logTransactions();

        File file = new File("transactionsLog.csv");

        assertTrue("File does not exist :)",file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }

    }

    @Test
    public void testGetLastTransaction() throws Exception {
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.createTransaction(TransactionType.WITHDRAWL, 1, 200.00);

        Transaction lastTrans = manager.getLastTransaction();

        assertEquals(EXPECTED_AMOUNT, lastTrans.getAmount(), .2);
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.createTransaction(TransactionType.WITHDRAWL, 1, 200.00);

        assertNotNull(manager.getAllTransactions());
    }

    @Test
    public void testGetRangeTransactions() throws Exception {
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.createTransaction(TransactionType.WITHDRAWL, 1, 200.00);

        Date from = new Date(1454945667);
        Date to = new Date(1455034958);
        ArrayList<Transaction> transactions = manager.getRangeTransactions(from, to );

        for(Transaction trans : transactions){
            assertTrue(trans.getDate().getTime() >= from.getTime() && trans.getDate().getTime() <= to.getTime());
        }
    }

    @Test
    public void testGetSpecificTransaction() throws Exception {
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.createTransaction(TransactionType.WITHDRAWL, 1, 200.00);

        Transaction transaction = manager.getLastTransaction();

        Transaction actual = manager.getSpecificTransaction(transaction.getId());

        assertEquals(transaction, actual);

    }
}