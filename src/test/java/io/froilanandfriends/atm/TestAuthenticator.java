package io.froilanandfriends.atm;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestAuthenticator {
    UserManager useM = UserManager.getUserManager();
    Authenticator auth = Authenticator.getAuthenticator();
    @Test
    public void testValidateUser(){
        UserManager userManager = UserManager.getUserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        try {
            useM.addUser("jbutts12","J","R","j@gmail.com",1111,"applesauce?","yes");
        } catch (Exception e){}

        User testUser = auth.validateUser("kbutts12");
        assertNull("validateUser() test failed",testUser);

        testUser = auth.validateUser("jbutts12");
        assertNotNull("validateUser() test failed",testUser);
        assertTrue("validateUser() test failed",testUser.getEmail().equals("j@gmail.com"));

        System.out.println("All validateUser() tests passed.");
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testAuthenticate(){
        UserManager userManager = UserManager.getUserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        try {
            useM.addUser("jbutts13","J","R","j@gmail.com",1111,"applesauce?","yes");
        } catch (Exception e){}

        boolean answer = auth.authenticate("jbutts13",1110);
        assertTrue("authenticate() test failed",answer==false);
        answer = auth.authenticate("jbutts2",1111);
        assertTrue("authenticate() test failed",answer==false);
        answer = auth.authenticate("jbutts13",1111);
        assertTrue("authenticate() test failed",answer==true);

        System.out.println("All authenticate() tests passed.");
        UserManager.setPATHNAME("userLog.csv");
    }
}