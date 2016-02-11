package io.froilanandfriends.atm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by willhorton on 2/10/16.
 */
public class LoginMenuTest {

    @Test
    public void testRemoveIllegalCharacters() throws Exception {
        assertTrue(LoginMenu.findIllegalCharacters("tom's"));
        assertTrue(LoginMenu.findIllegalCharacters("mord.ecai!"));
        assertTrue(LoginMenu.findIllegalCharacters("skill@@k"));
    }

    @Test
    public void testValidateEmail() throws Exception {
        assertTrue(LoginMenu.validateEmail("test@domain.com"));
        assertTrue(LoginMenu.validateEmail("test1domain_4@domain.org"));
        assertFalse(LoginMenu.validateEmail("testdomain.com"));
        assertFalse(LoginMenu.validateEmail("testtest"));
        assertFalse(LoginMenu.validateEmail(".test@test"));
        assertFalse(LoginMenu.validateEmail("@test.test"));
    }
}