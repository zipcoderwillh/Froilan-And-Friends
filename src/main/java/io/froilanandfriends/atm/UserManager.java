package io.froilanandfriends.atm;
import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 * This class manages all users currently in the ATM's database.
 */

public class UserManager {
    private User currentUser;
    private ArrayList<User> allUsers = new ArrayList<User>();
    private static String PATHNAME = "userLog.csv";

    //Singleton Setup
    private static UserManager current = new UserManager();
    UserManager(){ }

    /**
     * Accessor method for the current singleton instance.
     * @return The current singleton instance of UserManager.
     */
    public static UserManager getUserManager(){
        return current;
    }

    /*** FROM FILEIO ***/
    /**
     * Receives long string passed from {@link FileIO}, parses by line, and sends each line to constructor. Runs on ATM
     * bootup to load existing users.
     * @throws Exception
     */
    public void loadUsers() throws Exception {
        String[] hrSeparated = FileIO.readRecords(PATHNAME).split("\\n");
        for(String a : hrSeparated){
            allUsers.add(new User(a));
        }
    }

    /**
     * Outputs each user with all of their attributes to persistent storage.
     * @throws Exception
     */
    // For each User in UserArrayList, add User attributes to stringbuilder, send long string to FILEIO.
    public void logUsers () throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for(User x : allUsers){
            stringBuilder.append(x.getUserName()).append(",");
            stringBuilder.append(x.getUserID()).append(",");
            stringBuilder.append(x.getFirstName()).append(",");
            stringBuilder.append(x.getLastName()).append(",");
            stringBuilder.append(x.getEmail()).append(",");
            stringBuilder.append(x.getPin()).append(",");
            stringBuilder.append(x.getSecurityQuestion()).append(",");
            stringBuilder.append(x.getSecurityAnswer()).append(",");
            stringBuilder.append(x.isFlagged()).append(",");
            stringBuilder.append(x.isAdmin());
            stringBuilder.append("\n");

        }
        FileIO.logRecords(stringBuilder.toString(),PATHNAME);
    }
    /*** END OF FILEIO  ***/
    /**
     * Resets the current user to null.
     */
    public void clearUser(){ //removes currentUser
        currentUser = null;
    }

    /**
     * Sets the path to the external storage file containing all users.
     * @param PATHNAME Path to external storage.
     */
    public static void setPATHNAME(String PATHNAME) {
        UserManager.PATHNAME = PATHNAME;
    }

    /**
     * Flags a user for potential fraudulent activity.
     * @param userToFlag
     * @throws Exception
     */
    public void flagUser(User userToFlag) throws Exception { //flags a user
        userToFlag.setFlagged();
        logUsers();
    }

    /**
     * Removes fraudulent activity flag from user.
     * @param UserToUnFlag
     * @throws Exception
     */
    public void unFlagUser(User UserToUnFlag) throws Exception{ //removes flag from user
        UserToUnFlag.removeFlagged();
        logUsers();
    }

    /**
     * Returns the current user.
     * @return Current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Returns an {@link ArrayList} of all Users.
     * @return All users.
     */
    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Searches for a user by username.
     * @param userName Username to search for.
     * @return
     */
    public User getUser(String userName){ //searches for a user based on userName
        for(int x=0;x<allUsers.size();x++){
            User thisUser = allUsers.get(x);
            if (thisUser.getUserName().equals(userName)){
                return thisUser;
            }
        }
        return null;
    }

    /**
     * Searches for user by email.
     * @param email Email address to search for.
     * @return
     */
    public User getUserByEmail(String email){ // searches for a user based on email
        for (int i =0; i<allUsers.size(); i++){
            User thisUser = allUsers.get(i);
            if(thisUser.getEmail().equals(email)){
                return thisUser;
            }
        }
        return null;
    }

    /**
     * Adds a user and all their attributes to the allUsers list.
     * @param userName
     * @param firstName
     * @param lastName
     * @param email
     * @param pin
     * @param securityQuestion
     * @param secruityAnswer
     * @throws Exception
     */
    public void addUser(String userName, String firstName, String lastName, String email, int pin, String securityQuestion, String secruityAnswer) throws Exception {
        User newUser = new User(userName, firstName, lastName, email, pin, securityQuestion, secruityAnswer);
        allUsers.add(newUser);
        logUsers();
    }

    /**
     * Removes a specific user from the allUsers list.
     * @param u User to remove.
     */
    public void removeCurrentUser(User u){
        allUsers.remove(u);
    }

    /**
     * Searches for a user by ID.
     * @param id User ID to search for.
     * @return
     */
    public User getUserByID(int id) {
        for (int i =0; i<allUsers.size(); i++){
        User thisUser = allUsers.get(i);
        if(thisUser.getUserID()==id){
            return thisUser;
        }
    }
        return null;
    }
}