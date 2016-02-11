package io.froilanandfriends.atm;

/**
 * This class houses all variables and methods related to individual users, including username, security questions,
 * flags for admin access or fraudulent activity, etc.
 */

public class User {
    private boolean isAdmin = false;
    private boolean flagged = false;
    UserManager um = UserManager.getUserManager();
    private int userID = um.getAllUsers().size(),pin;
    private String userName, firstName, lastName, email, securityQuestion, securityAnswer;

    /**
     * Authenticates a PIN number entered by a customer against the PIN of an existing User.
     * @param pin The PIN number passed to the method.
     * @return True if PIN number matches, false if it does not.
     */
    boolean authenticate(int pin){
        if(pin==this.pin){
            return true;
        }
        return false;
    }
    /*** FROM FILE IO  ***/
    /**
     * Constructor for loading an existing user from persistent storage on ATM bootup.
     * @param x Passed from {@link UserManager.loadUsers()}.
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
    /*** END OF FILE IO ***/

    /**
     * Constructor for creating an entirely new user.
     * @param userName Entered by user during account creation process.
     * @param firstName Entered by user during account creation process.
     * @param lastName Entered by user during account creation process.
     * @param email Entered by user during account creation process.
     * @param pin Entered by user during account creation process.
     * @param securityQuestion Entered by user during account creation process.
     * @param securityAnswer Entered by user during account creation process.
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
     * Sets fraudulent activity flag to true.
     */
    public void setFlagged(){
        flagged = true;

    }

    /**
     * Removes fraudulent activity flag.
     */
    public void removeFlagged(){
        flagged = false;
    }

    /**
     * Sets PIN number.
     * @param pin
     */
    public void setPin(int pin) {
        this.pin = pin;
    }

    /**
     * Accessor method for getting PIN number.
     * @return
     */
    public int getPin(){
        return this.pin;
    }

    /**
     * Flags user as having admin access.
     */
    public void setAsAdmin(){
        isAdmin = true;

    }

    /**
     * Removes admin flag.
     */
    public void removeAdminStatus(){
        isAdmin = false;
    }

    /**
     * Returns flag indicating whether user has admin access.
     * @return True or false.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Shows whether user has been flagged for fraudulent activity.
     * @return True or false.
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Sets the user's ID.
     * @param ID
     */
    public void setUserID(int ID){
        this.userID = ID;
    }

    /**
     * Returns the user's ID.
     * @return ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Returns the user's name.
     * @return User's name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the user's first name.
     * @return User's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the user's last name.
     * @return User's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the user's email.
     * @return User's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's security question.
     * @return User's security question.
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Returns the user's security answer.
     * @return User's security answer.
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }
}