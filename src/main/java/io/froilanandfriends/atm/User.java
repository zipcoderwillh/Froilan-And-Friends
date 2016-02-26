package io.froilanandfriends.atm;

/**
 * Represents an individual ATM user.
 */

public class User {
    private boolean isAdmin = false;
    private boolean flagged = false;
    UserManager um = UserManager.getUserManager();
    private int userID = um.getAllUsers().size(),pin;
    private String userName, firstName, lastName, email, securityQuestion, securityAnswer;

    boolean authenticate(int pin){
        if(pin==this.pin){
            return true;
        }
        return false;
    }

    /**
     * General constructor for loading purposes.
     * @param x comes from {@link UserManager.loadUsers()}.
     */
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

    /**
     * Constructor for new user.
     * @param userName
     * @param firstName
     * @param lastName
     * @param email
     * @param pin
     * @param securityQuestion
     * @param securityAnswer
     */
    public User(String userName, String firstName, String lastName, String email, int pin, String securityQuestion, String securityAnswer){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pin = pin;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.userID += 42;
    }

    /**
     * Setter method to flag a user.
     */
    public void setFlagged(){
        flagged = true;

    }

    /**
     * Removes flag from user.
     */
    public void removeFlagged() {
        flagged = false;

    }

    /**
     * Sets a user's PIN number.
     * @param pin
     */
    public void setPin(int pin)   {
        this.pin = pin;

    }

    /**
     * Getter method for user's PIN number.
     * @return
     */
    public int getPin(){
        return this.pin;
    }

    /**
     * Turns a user into an admin.
     */
    public void setAsAdmin(){
        isAdmin = true;

    }

    /**
     * Turns an admin into a normal user.
     */
    public void removeAdminStatus()  {
        isAdmin = false;

    }

    /**
     * Getter method to determine if user is an admin.
     * @return admin status.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Getter method to determine if user is flagged.
     * @return
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Sets user's ID.
     * @param ID
     */
    public void setUserID(int ID){
        this.userID = ID;
    }

    /**
     * Getter method for user's ID.
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter method for user's name.
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter method for user's first name.
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter method for user's last name.
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter method for user's email.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter method for user's security question.
     * @return
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Getter method for user's security answer.
     * @return
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }
}