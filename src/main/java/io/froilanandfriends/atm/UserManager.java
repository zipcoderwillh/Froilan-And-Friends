package io.froilanandfriends.atm;
import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 * Handles all ATM users.
 */

public class UserManager {
    private User currentUser;
    private ArrayList<User> allUsers = new ArrayList<User>();
    private static String PATHNAME = "userLog.csv";

    //Singleton Setup
    private static UserManager current = new UserManager();
    UserManager(){ }

    /**
     * Returns singleton instance of UserManager.
     * @return
     */
    public static UserManager getUserManager(){
        return current;
    }

    /**
     * Receives long String from FILEIO, breaks by line, sends each line to constructor.
     */
    public void loadUsers() throws Exception {
        String[] hrSeparated = FileIO.readRecords(PATHNAME).split("\\n");
        for(String a : hrSeparated){
            allUsers.add(new User(a));
        }
    }

    /**
     * For each User in UserArrayList, add User attributes to stringbuilder, send long string to FILEIO.
     * @throws Exception
     */
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

    /**
     * Sets current user to null.
     */
    public void clearUser(){ //removes currentUser
        currentUser = null;
    }

    /**
     * Setter method for current file storage pathname.
     * @param PATHNAME
     */
    public static void setPATHNAME(String PATHNAME) {
        UserManager.PATHNAME = PATHNAME;
    }

    /**
     * Flags a specified user.
     * @param userToFlag
     * @throws Exception
     */
    public void flagUser(User userToFlag) throws Exception { //flags a user
        userToFlag.setFlagged();
        logUsers();
    }

    /**
     * Unflags a user.
     * @param UserToUnFlag
     * @throws Exception
     */
    public void unFlagUser(User UserToUnFlag) throws Exception{ //removes flag from user
        UserToUnFlag.removeFlagged();
        logUsers();
    }

    /**
     * Gets the current user.
     * @return
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
     * Returns a list of all users.
     * @return
     */
    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Searches for a user by userName.
     * @param userName
     * @return
     */
    public User getUser(String userName){
        for(int x=0;x<allUsers.size();x++){
            User thisUser = allUsers.get(x);
            if (thisUser.getUserName().equals(userName)){
                return thisUser;
            }
        }
        return null;
    }

    /**
     * Searches for a user by email.
     * @param email
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
     * Adds a user to the allusers list.
     * @param userName
     * @param firstName
     * @param lastName
     * @param email
     * @param pin
     * @param securityQuestion
     * @param securityAnswer
     * @throws Exception
     */
    public void addUser(String userName, String firstName, String lastName, String email, int pin, String securityQuestion, String securityAnswer) throws Exception {
        User newUser = new User(userName, firstName, lastName, email, pin, securityQuestion, securityAnswer);
        allUsers.add(newUser);
        logUsers();

    }

    /**
     * Removes specific user from list.
     * @param u
     */
    public void removeCurrentUser(User u){
        allUsers.remove(u);
    }

    /**
     * Searches for a user by ID.
     * @param id
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

    /**
     * Returns all currently flagged users.
     * @return
     */
    public ArrayList<User> getFlaggedUsers (){
        ArrayList<User> listToReturn = new ArrayList<User>();
        for(User thisUser:allUsers){
            if (thisUser.isFlagged()){
                listToReturn.add(thisUser);
            }
        }
        return listToReturn;
    }
}