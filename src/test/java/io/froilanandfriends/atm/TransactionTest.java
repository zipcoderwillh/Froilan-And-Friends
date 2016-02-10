package io.froilanandfriends.atm;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Doc on 2/9/16.
 */
public class TransactionTest {
    public final static Date EXPECTED_DATE = new Date();
    public final static int EXPECTED_TO_ACCOUNT = 1;
    public final static long EXPECTED_FROM_ACCOUNT = 2;
    public final static double EXPECTED_AMOUNT = 100.00;
    public final static TransactionType EXPECTED_TYPE = TransactionType.TRANSFER;

    @Test
    public void testCustomConstructor(){
        /**if this constructor works, there will be a transaction object with an accountFrom# 12345. Passes **/

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, 12345, 12345, 1111, new Date(), 111111);
        assertEquals(12345, transaction.getFromAccount());
    }

    @Test
    public void testGetDate() throws Exception {


        Transaction transaction = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        transaction.setDate(EXPECTED_DATE);

        assertEquals("Date not as expected", EXPECTED_DATE, transaction.getDate());
    }

    @Test
    public void testGetFromAccount() throws Exception {
        Transaction transaction = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        assertEquals("From account not as expected", EXPECTED_FROM_ACCOUNT, transaction.getFromAccount());
    }

    @Test
    public void testGetToAccount() throws Exception {
        Transaction transaction = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        assertEquals("To Account not as expected", EXPECTED_TO_ACCOUNT, transaction.getToAccount());
    }

    @Test
    public void testGetAmount() throws Exception {
        Transaction transaction = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        assertEquals("Amount not as expected", EXPECTED_AMOUNT, transaction.getAmount(), .022);
    }

    @Test
    public void testGetId() throws Exception {
        Transaction transaction = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        Transaction transaction2 = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        assertNotEquals("Multiple transactions with the same ID", transaction, transaction2);
    }

    @Test
    public void testGetTransactionType() throws Exception {
        Transaction transaction = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        assertEquals("Transaction Type not as expected", EXPECTED_TYPE, transaction.getTransactionType());
    }

    @Test
    public void testSetDate() throws Exception {
        Date currentTime = new Date();

        Transaction transaction = new Transaction(EXPECTED_TYPE, EXPECTED_FROM_ACCOUNT, EXPECTED_TO_ACCOUNT, EXPECTED_AMOUNT);
        transaction.setDate(currentTime);

        assertEquals("Date not as expected", currentTime, transaction.getDate());
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
}
