package io.froilanandfriends.atm;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class UserManager {
    private User currentUser;
    private ArrayList<User> allUsers = new ArrayList<User>();
    private static String PATHNAME = "userLog.csv";

    //Singleton Setup
    private static UserManager current = new UserManager();
    UserManager(){ }
    public static UserManager getUserManager(){
        return current;
    }

    /*** FROM FILEIO ***/
    // Receives long String from FILEIO, breaks by line, sends each line to Constructor.
    public void loadUsers() throws Exception {
        String[] hrSeparated = FileIO.readRecords(PATHNAME).split("\\n");
        for(String a : hrSeparated){
            allUsers.add(new User(a));
        }
    }
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
    public void clearUser(){ //removes currentUser
        currentUser = null;
    }

    public static void setPATHNAME(String PATHNAME) {
        UserManager.PATHNAME = PATHNAME;
    }

    public void flagUser(User userToFlag) throws Exception { //flags a user
        userToFlag.setFlagged();
        logUsers();
    }
    public void unFlagUser(User UserToUnFlag) throws Exception{ //removes flag from user
        UserToUnFlag.removeFlagged();
        logUsers();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public User getUser(String userName){ //searches for a user based on userName
        for(int x=0;x<allUsers.size();x++){
            User thisUser = allUsers.get(x);
            if (thisUser.getUserName().equals(userName)){
                return thisUser;
            }
        }
        return null;
    }

    public User getUserByEmail(String email){ // searches for a user based on email
        for (int i =0; i<allUsers.size(); i++){
            User thisUser = allUsers.get(i);
            if(thisUser.getEmail().equals(email)){
                return thisUser;
            }
        }
        return null;
    }

    //adds a user to the allusers list
    public void addUser(String userName, String firstName, String lastName, String email, int pin, String securityQuestion, String secruityAnswer) throws Exception {
        User newUser = new User(userName, firstName, lastName, email, pin, securityQuestion, secruityAnswer);
        allUsers.add(newUser);



        logUsers();

    }
    //will remove specific user from list
    public void removeCurrentUser(User u){
        allUsers.remove(u);
    }
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