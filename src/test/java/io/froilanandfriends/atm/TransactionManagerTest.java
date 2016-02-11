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
        /**
         we set the PATHNAME to another file and write in a string formatted the same
         way any transaction string in the usual log file would be. If load transaction
         is successful there will be a transaction object made with the details supplied in
         that string, and it will be the last transaction in the  allTransaction array. Test works
         like a charm
         **/
        TransactionManager manager = TransactionManager.getTransactionManager();
        manager.setPATHNAME("testTransactionLog.csv");
        manager.loadTransactions();



        Transaction transaction =  manager.getLastTransaction();
        assertEquals(20066, transaction.getFromAccount());
        assertTrue("WITHDRAWL".equals(transaction.getTransactionType().toString()));
        assertEquals(20233, transaction.getToAccount());
        assertEquals(8000.0, transaction.getAmount(), 0.01);
        assertTrue("Wed Feb 10 14:41:27 EST 2016".equals(transaction.getDate().toString()));
        assertEquals(12, transaction.getId());
        manager.setPATHNAME("transactionsLog.csv");


    }

    @Test
    public void testLogTransactions() throws Exception {
        /** test shows that after we stock manager's transaction array, logTransactions()
         * can take those objects create a long string with them and write it to
         * a file. The test shows the file exists and we can see the contents are in order
         * when we print out
         **/
        TransactionManager manager = TransactionManager.getTransactionManager();
        //                                        type      from      to    amount
        manager.setPATHNAME("testTransactionLog.csv");
        manager.createTransaction(TransactionType.DEPOSIT, 1899888, 1888181, 80000);
        manager.createTransaction(TransactionType.DEPOSIT, 1899888, 1888181, 80000);
        manager.createTransaction(TransactionType.DEPOSIT, 1899888, 1888181, 80000);
        manager.logTransactions();

        File file = new File("testTransactionLog.csv");

        assertTrue("File does not exist :)",file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            stringBuilder.append(line + "\n");

        }
        String bigString = stringBuilder.toString().trim();

        String[] targetArr = bigString.split("\n");
        int last = targetArr.length - 1;
        String[] lastArr = targetArr[last].split(",");
        assertTrue(lastArr[0],lastArr[0].equals("DEPOSIT"));
        assertTrue(lastArr[1].equals("1899888"));
        assertTrue(lastArr[2].equals("1888181"));
        assertTrue(lastArr[3].equals("80000.0"));
        manager.setPATHNAME("transactionsLog.csv");
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