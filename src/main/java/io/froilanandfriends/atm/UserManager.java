package io.froilanandfriends.atm;
import java.util.ArrayList;

public class UserManager {
    private User currentUser;
    private ArrayList<User> allUsers = new ArrayList<User>();

    //Singleton Setup
    private static UserManager current = new UserManager();
    UserManager(){ }
    public static UserManager getUserManager(){
        return current;
    }

    public void loadUsers(){
        //calls setUsers from fileIO, which sends back a huge string of users
        //iterates over string to populate allUsers

    }
    public void clearUser(){ //removes currentUser
        currentUser = null;
    }


    public void flagUser(User userToFlag){ //flags a user
        userToFlag.setFlagged();
    }
    public void unFlagUser(User UserToUnFlag){ //removes flag from user
        UserToUnFlag.removeFlagged();

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
    public void addUser(String userName, String firstName, String lastName, String email, int pin, String securityQuestion, String secruityAnswer){
        User newUser = new User(userName, firstName, lastName, email, pin, securityQuestion, secruityAnswer);
        allUsers.add(newUser);

    }
    //will remove specific user from list
    public void removeCurrentUser(User u){
        allUsers.remove(u);
    }
}