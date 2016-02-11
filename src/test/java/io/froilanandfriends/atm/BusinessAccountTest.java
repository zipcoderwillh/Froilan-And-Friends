package io.froilanandfriends.atm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shaungould on 2/11/16.
 */
public class BusinessAccountTest {

    @Test
    public void testConstructorWithStringArg(){
        BusinessAccount businessAccount = new BusinessAccount("BUSINESS,2417744461260,0.0,42,43");
        assertEquals(43, (int)businessAccount.getUserIDs().get(1));

    }
}