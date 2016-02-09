package io.froilanandfriends.atm;

import org.junit.Test;

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
}
