package io.froilanandfriends.atm;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

import java.util.*;

/**
 * Created by nicholaswebb on 2/9/16.
 */
public class UserManagerTest {


    @Test
    public void testAddUser() throws Exception {

        UserManager userManager = new UserManager();
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        assertNotNull("User should be added to the list", userManager.getAllUsers().get(0));
    }

    @Test
    public void testRemoveUser() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.removeCurrentUser(userManager.getCurrentUser());
        assertNotNull("Current user should no longer be in the list", userManager.getAllUsers().get(0));
    }

    @Test
    public void testFlagging() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");

        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.flagUser(userManager.getCurrentUser());
        assertTrue("User should now be flaged", userManager.getCurrentUser().isFlagged());
        userManager.unFlagUser(userManager.getCurrentUser());
        assertFalse("User should now be unFlagged", userManager.getCurrentUser().isFlagged());

    }
    @Test
    public void testSearchCurrentUserByUserName() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com",5847 , "Where are you from?", "testville");

        User tester = userManager.getUser("nwebb89");
        assertEquals("tester should be our first user", tester, userManager.getAllUsers().get(0));
    }

    @Test
    public void testSerchCurrentUserByEmail() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");

        User tester = userManager.getUserByEmail("tester@yahoo.com");
        assertEquals("tester should be our second user", tester, userManager.getAllUsers().get(1));
    }

    @Test
    public void testGetAllUsers() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        ArrayList<User> testUsers = userManager.getAllUsers();
        assertEquals("testUsers should be two big", testUsers.size(), userManager.getAllUsers().size());
        userManager.addUser("nwebb891", "Nick1", "Webb1", "TestEmail1@gmail.com", 3477, "What is your dogs name?","Tucker");
        userManager.addUser("testman1", "Test1", "Man1", "tester1@yahoo.com", 5174, "Where are you from?", "testville");

        assertEquals("testUsers should be four big", testUsers.size(), userManager.getAllUsers().size());


    }
    @Test
    public void testGetCurrentUser() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(1));
        assertEquals("Index 1 should be our current user",userManager.getAllUsers().get(1) ,userManager.getCurrentUser());

    }

    @Test
    public void testAdminSupport() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.getCurrentUser().setAsAdmin();
        assertTrue("User should be an Admin", userManager.getCurrentUser().isAdmin());
        assertFalse("User should not be an Admin", userManager.getAllUsers().get(1).isAdmin());
    }

    @Test
    public void testAdminRemoval() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.getCurrentUser().setAsAdmin();
        assertTrue("User is admin", userManager.getCurrentUser().isAdmin());
        userManager.getCurrentUser().removeAdminStatus();
        assertFalse("User should no longer be admin", userManager.getCurrentUser().isAdmin());
    }

    @Test
    public void testPinSetting() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));

        userManager.getCurrentUser().setPin(1111);

        assertEquals("pin should be 1111", 1111, userManager.getCurrentUser().getPin());
    }


    @Test
    public void testGettingUserInformation() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));

        assertEquals("First names should match", "Nick", userManager.getCurrentUser().getFirstName().toString());
        assertEquals("Last names should match", "Webb", userManager.getCurrentUser().getLastName().toString());
    }


    @Test
    public void testSecurityQuestions() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");

        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        assertEquals("Security question should match", "What is your dogs name?", userManager.getCurrentUser().getSecurityQuestion());
        assertEquals("secruity answer should match", "Tucker", userManager.getCurrentUser().getSecurityAnswer());
        assertNotEquals("Current users question shouldn't be second users", "Where are you from", userManager.getCurrentUser().getSecurityQuestion());
        assertNotEquals("Current users answer shouldn't be second users", "testville", userManager.getCurrentUser().getSecurityAnswer());
    }
    @Test
    public void testClearUsers() throws Exception{
        UserManager userManager = new UserManager();

        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");

        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.clearUser();
        assertNull("Current user should no longer exist", userManager.getCurrentUser());

    }
}