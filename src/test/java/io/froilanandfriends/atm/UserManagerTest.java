package io.froilanandfriends.atm;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by nicholaswebb on 2/9/16.
 */
public class UserManagerTest {


    @Test
    public void testAddUser() throws Exception {

        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        assertTrue(userManager.getAllUsers().get(0).getFirstName().equals("Nick"));
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testRemoveUser() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.removeCurrentUser(userManager.getCurrentUser());
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        assertTrue(userManager.getAllUsers().get(0).getFirstName().equals("Test"));
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testFlagging() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.flagUser(userManager.getCurrentUser());
        userManager.logUsers();
        assertTrue("User should now be flagged", userManager.getCurrentUser().isFlagged());
        userManager.unFlagUser(userManager.getCurrentUser());
        userManager.logUsers();
        assertFalse("User should now be unFlagged", userManager.getCurrentUser().isFlagged());
        UserManager.setPATHNAME("userLog.csv");
    }
    @Test
    public void testSearchCurrentUserByUserName() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com",5847 , "Where are you from?", "testville");
        User tester = userManager.getUser("nwebb89");
        assertEquals("tester should be our first user", tester, userManager.getAllUsers().get(0));
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testSearchCurrentUserByEmail() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        User tester = userManager.getUserByEmail("tester@yahoo.com");
        assertEquals("tester should be our second user", tester, userManager.getAllUsers().get(1));
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testGetAllUsers() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        ArrayList<User> testUsers = userManager.getAllUsers();
        assertEquals("testUsers should be two big", testUsers.size(), userManager.getAllUsers().size());
        userManager.addUser("nwebb891", "Nick1", "Webb1", "TestEmail1@gmail.com", 3477, "What is your dogs name?","Tucker");
        userManager.addUser("testman1", "Test1", "Man1", "tester1@yahoo.com", 5174, "Where are you from?", "testville");
        assertEquals("testUsers should be four big", testUsers.size(), userManager.getAllUsers().size());
        UserManager.setPATHNAME("userLog.csv");

    }
    @Test
    public void testGetCurrentUser() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(1));
        assertEquals("Index 1 should be our current user",userManager.getAllUsers().get(1) ,userManager.getCurrentUser());
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testAdminSupport() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.getCurrentUser().setAsAdmin();
        userManager.logUsers();
        assertTrue("User should be an Admin", userManager.getCurrentUser().isAdmin());
        assertFalse("User should not be an Admin", userManager.getAllUsers().get(1).isAdmin());
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testAdminRemoval() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.getCurrentUser().setAsAdmin();
        userManager.logUsers();
        assertTrue("User is admin", userManager.getCurrentUser().isAdmin());
        userManager.getCurrentUser().removeAdminStatus();
        userManager.logUsers();
        assertFalse("User should no longer be admin", userManager.getCurrentUser().isAdmin());
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testPinSetting() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.getCurrentUser().setPin(1111);
        userManager.logUsers();
        assertEquals("pin should be 1111", 1111, userManager.getCurrentUser().getPin());
        UserManager.setPATHNAME("userLog.csv");
    }


    @Test
    public void testGettingUserInformation() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        assertEquals("First names should match", "Nick", userManager.getCurrentUser().getFirstName());
        assertEquals("Last names should match", "Webb", userManager.getCurrentUser().getLastName());
        UserManager.setPATHNAME("userLog.csv");
    }


    @Test
    public void testSecurityQuestions() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        assertEquals("Security question should match", "What is your dogs name?", userManager.getCurrentUser().getSecurityQuestion());
        assertEquals("secruity answer should match", "Tucker", userManager.getCurrentUser().getSecurityAnswer());
        assertNotEquals("Current users question shouldn't be second users", "Where are you from", userManager.getCurrentUser().getSecurityQuestion());
        assertNotEquals("Current users answer shouldn't be second users", "testville", userManager.getCurrentUser().getSecurityAnswer());
        UserManager.setPATHNAME("userLog.csv");
    }
    @Test
    public void testClearUsers() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");

        userManager.setCurrentUser(userManager.getAllUsers().get(0));
        userManager.clearUser();
        assertNull("Current user should no longer exist", userManager.getCurrentUser());
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testSetSequentialUserIDs() throws Exception{
        UserManager userManager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        userManager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        System.out.println(userManager.getAllUsers().get(0).getUserID());
        userManager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        System.out.println(userManager.getAllUsers().get(1).getUserID());
        int user1ID = userManager.getAllUsers().get(0).getUserID();
        int user2ID = userManager.getAllUsers().get(1).getUserID();

        assertTrue(user2ID-user1ID == 1);
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testLoadUsers() throws Exception {

        UserManager manager = new UserManager();
        UserManager.setPATHNAME("testLoadUserLog.csv");
        manager.loadUsers();
        manager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        manager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");

        int last = manager.getAllUsers().size() - 1;
        User user =  manager.getAllUsers().get(last);
        Assert.assertEquals(42+manager.getAllUsers().size(),user.getUserID() );
        UserManager.setPATHNAME("userLog.csv");
    }

    @Test
    public void testLogAccounts() throws Exception {
        UserManager manager = new UserManager();
        UserManager.setPATHNAME("testLogUserLog.csv");
        manager.addUser("nwebb89", "Nick", "Webb", "TestEmail@gmail.com", 4477, "What is your dogs name?","Tucker");
        manager.addUser("testman", "Test", "Man", "tester@yahoo.com", 5874, "Where are you from?", "testville");
        manager.logUsers();


        BufferedReader br = new BufferedReader(new FileReader("testLogUserLog.csv"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line + "\n");

        }
        String bigString = stringBuilder.toString().trim();
        String[] targetArr = bigString.split("\n");
        int last = targetArr.length - 1;
        String[] lastArr = targetArr[last].split(",");
        Assert.assertTrue(lastArr[0], lastArr[0].equals("testman"));
        Assert.assertTrue(lastArr[2].equals("Test"));
        Assert.assertTrue(lastArr[3].equals("Man"));
        Assert.assertTrue(lastArr[4].equals("tester@yahoo.com"));
        Assert.assertTrue(lastArr[5].equals(5874 + ""));
        br.close();
        UserManager.setPATHNAME("userLog.csv");
    }
}