package io.froilanandfriends.atm;



public class User {
    private boolean isAdmin = false;
    private boolean flagged = false;
    private int userID,pin;
    private String userName, firstName, lastName, email, securityQuestion, securityAnswer;

    boolean authenticate(int pin){
        if(pin==this.pin){
            return true;
        }
        return false;
    }
    /*** FROM FILE IO  ***/
    //Added general constructor for loading purposes. String x comes from UserManager.loadUsers().
    public User(String x){
        String[] cSeparated = x.split(",");
        this.userName  = cSeparated[0];
        this.userID  = Integer.parseInt(cSeparated[1]);
        this.firstName  = cSeparated[2];
        this.lastName  = cSeparated[3];
        this.email  = cSeparated[4];
        this.pin  = Integer.parseInt(cSeparated[5]);
        this.securityQuestion  = cSeparated[6];
        this.securityAnswer  = cSeparated[7];
        this.flagged  = Boolean.parseBoolean(cSeparated[8]);
        this.isAdmin  = Boolean.parseBoolean(cSeparated[9]);
    }

    public User(String userName, String firstName, String lastName, String email, int pin, String securityQuestion, String securityAnswer){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pin = pin;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public void setFlagged(){
        flagged = true;
    }

    public void removeFlagged(){
        flagged = false;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getPin(){
        return this.pin;
    }
    public void setAsAdmin(){
        isAdmin = true;
    }

    public void removeAdminStatus(){
        isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setUserID(int ID){
        this.userID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
}