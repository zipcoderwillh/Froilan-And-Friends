package io.froilanandfriends.atm;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestAuthenticator {
    UserManager useM = UserManager.getUserManager();
    Authenticator auth = Authenticator.getAuthenticator();
    @Test
    public void testValidateUser(){
        useM.addUser("jbutts12","J","R","j@gmail.com",1111,"applesauce?","yes");
        User testUser = auth.validateUser("kbutts12");
        assertNull("validateUser() test failed",testUser);

        testUser = auth.validateUser("jbutts12");
        assertNotNull("validateUser() test failed",testUser);
        assertTrue("validateUser() test failed",testUser.getEmail().equals("j@gmail.com"));

        System.out.println("All validateUser() tests passed.");
    }

    @Test
    public void testAuthenticate(){
        useM.addUser("jbutts12","J","R","j@gmail.com",1111,"applesauce?","yes");
        boolean answer = auth.authenticate("jbutts12",1110);
        assertTrue("authenticate() test failed",answer=false);
        answer = auth.authenticate("jbutts2",1111);
        assertTrue("authenticate() test failed",answer=false);
        answer = auth.authenticate("jbutts12",1111);
        assertTrue("authenticate() test failed",answer=true);

        System.out.println("All authenticate() tests passed.");
    }
}