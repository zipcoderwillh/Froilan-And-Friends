package io.froilanandfriends.atm;

import org.junit.Test;

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